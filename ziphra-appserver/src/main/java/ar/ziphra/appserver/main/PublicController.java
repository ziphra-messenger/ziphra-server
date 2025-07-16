package ar.ziphra.appserver.main;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.common.dto.ProtocoloWrapperDTO;
import ar.ziphra.commonback.common.enumeration.ServerUrls;
import ar.ziphra.commonback.common.utils.AESToUse;
import ar.ziphra.appserver.component.common.ControllerBase;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * PublicController handles external protocol requests to the system via
 * the `/public/entry` endpoint.
 * <p>
 * Incoming requests are encrypted using AES (with AES parameters encrypted via RSA).
 * This controller decrypts the AES keys and the request payload, processes it
 * using the inherited protocol logic, and returns an AES-encrypted + compressed response.
 * </p>
 *
 * <p>
 * Author: Jorge Kagiagian
 * </p>
 */
@RestController
@RequestMapping(path = "/public")
@Slf4j
public class PublicController extends ControllerBase {

    /**
     * AES encryption bit length (configured in application properties).
     */
    @Value("${serverconf.ziphraIdAes.bits}")
    private int bitsEncrypt;

    /**
     * Provides access to system-wide services, cryptographic tools, and utilities.
     */
    private final FacadeComponent comps;

    /**
     * Constructor with injected service facade.
     *
     * @param comps central access point to services and helpers
     */
    public PublicController(FacadeComponent comps) {
        this.comps = comps;
    }

    /**
     * Main entry point for public protocol communication.
     * Decrypts the AES parameters (salt, key, iteration) using RSA,
     * decrypts the ProtocoloDTO payload using AES,
     * processes the request through base controller logic,
     * and returns an AES-encrypted + compressed response string.
     *
     * @param wrapper encrypted protocol wrapper (AES + payload)
     * @return encrypted and compressed response
     * @throws Exception on decryption or processing failure
     */
    @PostMapping("/entry")
    public ResponseEntity<String> in(@RequestBody ProtocoloWrapperDTO wrapper) throws Exception {
        // Step 1: Decrypt AES parameters
        AESDTO aesDTO = buildAESDTO(wrapper);
        AESToUse aes = new AESToUse(aesDTO);

        // Step 2: Decrypt ProtocoloDTO JSON payload
        String decryptedProtocoloJson = aes.getAESDecrypt(wrapper.getProtocoloDTO());

        // Step 3: Deserialize decrypted JSON to object
        ProtocoloDTO protocoloRequest = comps.util().gson().fromJson(decryptedProtocoloJson, ProtocoloDTO.class);

        // Step 4: Process the request using base protocol logic
        ProtocoloDTO protocoloResponse = super.in(protocoloRequest);

        // Step 5: Serialize, encrypt, and compress the response
        String responseJson = comps.util().string().gsonToSend(protocoloResponse);
        String encryptedCompressedResponse = aes.getAES(responseJson);
        String finalResponse = comps.util().string().gsonToSendCompress(encryptedCompressedResponse);

        log.debug("PublicController OUT >> {}", comps.util().string().cutString(finalResponse));
        return ResponseEntity.ok(finalResponse);
    }

    /**
     * Builds the AESDTO object using RSA-decrypted values from the wrapper.
     *
     * @param wrapper the request wrapper with encrypted AES fields
     * @return fully initialized AESDTO
     * @throws Exception if RSA decryption fails
     */
    private AESDTO buildAESDTO(ProtocoloWrapperDTO wrapper) throws Exception {
        String salt = decryptRSA(wrapper.getAesEncripted().getSaltAES());
        String key = decryptRSA(wrapper.getAesEncripted().getSecretKeyAES());
        String iteration = decryptRSA(wrapper.getAesEncripted().getIteration());

        return new AESDTO()
            .setBitsEncrypt(String.valueOf(bitsEncrypt))
            .setSaltAES(salt)
            .setSecretKeyAES(key)
            .setIteration(iteration);
    }

    /**
     * Helper method to decrypt RSA-encrypted Base64 string.
     *
     * @param encryptedBase64 base64-encoded string encrypted with RSA
     * @return decrypted plain text
     * @throws Exception if decryption fails
     */
    private String decryptRSA(String encryptedBase64) throws Exception {
        return comps.common().ziphraRSA().desencrypt(Base64.decode(encryptedBase64));
    }

    /**
     * Indicates whether this controller operates in a secure context.
     *
     * @return false, since this is a public entry point
     */
    @Override
    public boolean isSecure() {
        return false;
    }

    /**
     * Indicates whether request ID validation is required.
     *
     * @return true, this controller expects request ID validation
     */
    @Override
    public boolean isRequestId() {
        return true;
    }

    /**
     * Provides the server URL scope used by this controller.
     *
     * @return public server URL constant
     */
    @Override
    public ServerUrls getUrl() {
        return ServerUrls.CONSTANT_URL_PATH_PUBLIC;
    }
}

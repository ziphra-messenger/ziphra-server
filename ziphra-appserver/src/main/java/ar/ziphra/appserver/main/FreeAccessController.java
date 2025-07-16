package ar.ziphra.appserver.main;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.commonback.common.enumeration.ServerUrls;
import ar.ziphra.appserver.component.common.ControllerBase;
import ar.ziphra.appserver.component.encryptkeys.ZiphraRSAValidation;
import ar.ziphra.appserver.component.serverconf.ServerConfValidationService;

/**
 * FreeAccessController provides access to unprotected endpoints that do not require
 * authentication or request ID validation.
 * <p>
 * It delegates protocol execution logic to the shared {@link ControllerBase}
 * and is mapped under the `/free` URL context.
 * </p>
 *
 * <p>
 * Author: Jorge Kagiagian
 * </p>
 */
@RestController
@RequestMapping(path = "/free")
public class FreeAccessController extends ControllerBase {

    /**
     * Service responsible for validating server configuration.
     */
    @SuppressWarnings("unused")
    private final ServerConfValidationService serverConfValidationService;

    /**
     * RSA validation component used to check key security or format.
     */
    @SuppressWarnings("unused")
    private final ZiphraRSAValidation ziphraRSAValidation;

    /**
     * Constructor with dependency injection.
     *
     * @param serverConfValidationService service for server config validation
     * @param ziphraRSAValidation RSA key validation component
     * @throws Exception on init failure
     */
    public FreeAccessController(
            ServerConfValidationService serverConfValidationService,
            ZiphraRSAValidation ziphraRSAValidation) throws Exception {

        this.serverConfValidationService = serverConfValidationService;
        this.ziphraRSAValidation = ziphraRSAValidation;
    }

    /**
     * Main entry point for protocol requests that do not require authentication.
     * This method directly delegates the logic to {@link ControllerBase#in(ProtocoloDTO)}.
     *
     * @param request incoming protocol request DTO
     * @return the processed protocol response
     */
    @PostMapping("/entry")
    public ProtocoloDTO in(@RequestBody ProtocoloDTO request) {
        return super.in(request);
    }

    /**
     * Specifies that this controller does not enforce authentication.
     *
     * @return false (public access)
     */
    @Override
    public boolean isSecure() {
        return false;
    }

    /**
     * Specifies that this controller does not require a request ID.
     *
     * @return false
     */
    @Override
    public boolean isRequestId() {
        return false;
    }

    /**
     * Returns the logical server URL namespace this controller handles.
     *
     * @return {@link ServerUrls#CONSTANT_URL_PATH_FREE}
     */
    @Override
    public ServerUrls getUrl() {
        return ServerUrls.CONSTANT_URL_PATH_FREE;
    }
}

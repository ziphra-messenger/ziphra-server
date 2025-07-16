package ar.ziphra.appserver.component.common;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.web.bind.annotation.RequestBody;

import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.enumeration.ProtocoloActionsEnum;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.common.interfaces.IdGrupoInterface;
import ar.ziphra.commonback.common.enumeration.ServerUrls;
import ar.ziphra.appserver.services.protocolomap.ProtocoloValue;
import lombok.extern.slf4j.Slf4j;

/**
 * Base controller class for handling protocol-based requests.
 * 
 * <p>This abstract class provides the core logic for decoding incoming protocol requests,
 * mapping them to corresponding service methods, invoking those methods, handling exceptions,
 * and preparing protocol responses.</p>
 * 
 * <p>Subclasses must specify security behavior and URL mapping.</p>
 * 
 * @author Jorge
 * @version 1.0
 * @since 2025-07-10
 */
@Slf4j
public abstract class ControllerBase extends ControllerBaseUtil {

    /**
     * Handles an incoming protocol request.
     * 
     * <p>It retrieves the mapped method based on the request, validates the request ID if required,
     * parses DTO parameters, invokes the target method, catches and handles exceptions, and
     * prepares the response DTO accordingly.</p>
     * 
     * @param request the incoming protocol DTO containing the request details
     * @return the protocol response DTO with result or error information
     */
    public ProtocoloDTO in(@RequestBody ProtocoloDTO request) {
        log.debug(">> ProtocoloDTO IN: {}", request);

        ProtocoloDTO response = new ProtocoloDTO();
        Object returnObject = null;

        try {
            ProtocoloValue value = comps.service().protocoloMap().get(getUrl(), request.getComponent(), request.getAction());
            if (value == null) {
                throw new ValidationException(ExceptionReturnCode.GENERAL_INVALID_ACCESS_PROTOCOL);
            }

            validateRequestId(request);

            if (value.getParametersType() == null) {
                returnObject = value.getMethod().invoke(value.getClazz());
            } else {
                Object dto = parseDTO(request.getObjectDTO(), value.getParametersType());

                if (dto instanceof IdGrupoInterface) {
                    comps.requestHelper().setGrupoInUse((IdGrupoInterface) dto);
                }

                returnObject = value.getMethod().invoke(value.getClazz(), dto);
            }

        } catch (Exception e) {
            handleException(response, e);
            return finalizeResponse(request, response);
        }

        prepareResponseData(response, request, returnObject);

        return finalizeResponse(request, response);
    }

    /**
     * Validates the request ID if the controller requires it.
     * 
     * <p>Checks the security mode and request action type to decide whether
     * to validate the request ID and ensures it exists and is valid.</p>
     * 
     * @param request the incoming protocol DTO to validate
     * @throws ZiphraException if the request ID is invalid or missing
     */
    private void validateRequestId(ProtocoloDTO request) throws ZiphraException {
        boolean secure = isSecure();
        boolean requiresRequestId = isRequestId();
        ProtocoloActionsEnum action = request.getAction();

        if (!requiresRequestId) return;

        boolean isRequestValid = secure
            ? !ProtocoloActionsEnum.REQUEST_ID_PRIVATE_GET.equals(action)
            : !ProtocoloActionsEnum.REQUEST_ID_PUBLIC_GET.equals(action);

        if (isRequestValid) {
            comps.util().requestId().existsRequestId(request.getRequestIdDTO(), secure);
        }
    }

    /**
     * Parses a JSON string into an object of the specified class.
     * 
     * @param json  JSON string representing the DTO
     * @param clazz target class to parse the JSON into
     * @return the parsed DTO object
     * @throws IOException if parsing the JSON fails
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Object parseDTO(String json, Class clazz) throws IOException {
        log.debug("Parsing DTO: {} to class {}", comps.util().string().cutString(json), clazz.getName());
        return comps.util().gson().fromJson(json, clazz);
    }

    /**
     * Handles exceptions occurring during method invocation.
     * 
     * <p>Logs the error and sets the response with appropriate error messages and codes.</p>
     * 
     * @param response the protocol response DTO to update with error information
     * @param e        the caught exception
     */
    private void handleException(ProtocoloDTO response, Exception e) {
        log.error("Error in protocol execution: {}", e.getMessage(), e);
        response.setObjectDTO(null);

        Throwable cause = e instanceof InvocationTargetException ? e.getCause() : e;

        if (cause instanceof ValidationException) {
            response.setMensajeRespuesta(((ValidationException) cause).getDescription());
        }

        response.setCodigoRespuesta(cause != null ? cause.getMessage() : "G_000");
        if (response.getMensajeRespuesta() == null) {
            response.setMensajeRespuesta("UNCLASSIFIED_ERROR");
        }
    }

    /**
     * Prepares the response data by converting the returned object to JSON or setting
     * the message DTO as needed.
     * 
     * @param response the protocol response DTO to populate
     * @param request  the original protocol request DTO
     * @param result   the object returned by the invoked method
     */
    private void prepareResponseData(ProtocoloDTO response, ProtocoloDTO request, Object result) {
        if (result instanceof MessageDTO) {
            response.setMessage((MessageDTO) result);
        } else {
            String jsonResult = result != null ? comps.util().gson().toJson(result) : null;
            log.debug("ProtocoloDTO OUT JSON: {}", comps.util().string().cutString(jsonResult));
            response.setObjectDTO(jsonResult);
        }
    }

    /**
     * Finalizes the response by copying metadata fields from the request.
     * 
     * @param request  the original protocol request DTO
     * @param response the response DTO to finalize
     * @return the finalized response DTO
     */
    private ProtocoloDTO finalizeResponse(ProtocoloDTO request, ProtocoloDTO response) {
        response.setComponent(request.getComponent());
        response.setAction(request.getAction());
        response.setAsyncId(request.getAsyncId());
        return response;
    }

    /**
     * Indicates whether the controller requires secure handling.
     * 
     * @return true if security is enabled, false otherwise
     */
    public abstract boolean isSecure();

    /**
     * Indicates whether the controller requires request ID validation.
     * 
     * @return true if request ID validation is required, false otherwise
     */
    public abstract boolean isRequestId();

    /**
     * Returns the ServerUrls enum value that identifies this controller's URL mapping.
     * 
     * @return the ServerUrls value for this controller
     */
    public abstract ServerUrls getUrl();
}

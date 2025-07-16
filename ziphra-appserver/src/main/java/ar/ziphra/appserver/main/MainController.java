package ar.ziphra.appserver.main;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ar.ziphra.commonback.common.enumeration.ServerUrls;
import lombok.extern.slf4j.Slf4j;

/**
 * MainController handles dynamic entry routing based on the provided path variable.
 * <p>
 * This acts as a unique entry point capable of redirecting to any internal
 * controller by resolving the requested controller name as an enum constant
 * in {@link ServerUrls}.
 * </p>
 * 
 * <p>
 * Author: Jorge Kagiagian
 * </p>
 */
@RestController
@RequestMapping(path = "/entry")
@Slf4j
public class MainController {

    /**
     * Dynamically forwards a request to another controller based on the path variable.
     * <p>
     * It logs all request headers and validates that the requested controller exists
     * as a constant in {@link ServerUrls}.
     * </p>
     *
     * @param controller the controller identifier to forward to (must match a ServerUrls constant)
     * @param headers    a map of all HTTP headers sent in the request
     * @return a {@link ModelAndView} object configured for internal forward
     * @throws IllegalArgumentException if the controller does not match any ServerUrls enum
     */
    @PostMapping("/{controller}")
    public ModelAndView in2(
            @PathVariable String controller,
            @RequestHeader Map<String, String> headers) {

        log.debug("Received unified entry POST call. Target controller: {}", controller);

        // Log all request headers at trace level
        log.trace("Header count: {}", headers.size());
        headers.forEach((key, value) ->
            log.trace("Header: {} - Value: {}", key, value)
        );

        try {
            // Validate and resolve controller enum
            ServerUrls targetUrl = Enum.valueOf(ServerUrls.class, controller);
            log.trace("Resolved controller to: {}", targetUrl);

            // Perform internal forward to the resolved controller
            return new ModelAndView("forward:" + targetUrl);

        } catch (IllegalArgumentException e) {
            // If controller name is not a valid enum, log error and rethrow
            log.error("Invalid controller path: '{}'. Must match a ServerUrls constant.", controller, e);
            throw e; // Let Spring handle 400 Bad Request
        }
    }
}

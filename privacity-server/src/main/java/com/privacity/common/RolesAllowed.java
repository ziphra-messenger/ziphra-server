package com.privacity.common;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.privacity.common.enumeration.GrupoRolesEnum;

@Documented
@Retention (RUNTIME)
@Target({TYPE, METHOD})
public @interface RolesAllowed {
    /**
     * List of roles that are permitted access.
     */
	GrupoRolesEnum[] value();
}
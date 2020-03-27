package com.vignesh.library.parser.apdu.annotation;

import com.vignesh.library.utils.BitUtils;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to describe field information
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Data {

	/**
	 * Format date
	 */
	String format() default BitUtils.DATE_FORMAT;

	/**
	 * The current date standard
	 */
	int dateStandard() default 0;

	/**
	 * index of data
	 */
	int index();

	/**
	 * Read the string in hexa
	 */
	boolean readHexa() default false;

	/**
	 * Number of bytes
	 */
	int size();

	/**
	 * Tag Name
	 */
	String tag();
}

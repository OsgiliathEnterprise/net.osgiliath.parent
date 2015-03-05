package net.osgiliath.helpers.cxf.exception.handling;

/*
 * #%L
 * Helper for CXF RS validation exceptions
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
/**
 * Constants for Exception mapping.
 * @author charliemordant
 *
 */
public final class ExceptionMappingConstants {
  /**
   * Private Ctor.
   */
  private ExceptionMappingConstants() {
    super();
  }
  /**
   * The exception body http header.
   */
  public static final String EXCEPTION_BODY_HEADER = "ExceptionBody";
  /**
   * The exception message property.
   */
  public static final String EXCEPTION_MESSAGE = "message";
}

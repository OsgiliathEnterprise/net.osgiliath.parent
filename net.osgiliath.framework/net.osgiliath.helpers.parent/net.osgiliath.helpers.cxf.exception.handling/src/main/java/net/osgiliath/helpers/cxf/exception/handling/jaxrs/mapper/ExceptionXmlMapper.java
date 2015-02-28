package net.osgiliath.helpers.cxf.exception.handling.jaxrs.mapper;

/*
 * #%L
 * net.osgiliath.helpers.cxf.exception.handling
 * %%
 * Copyright (C) 2013 Osgiliath corp
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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import net.osgiliath.helpers.cxf.exception.handling.ExceptionMappingConstants;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CXF exception Mapper
 * 
 * @author Charlie
 * 
 */
public class ExceptionXmlMapper implements ExceptionMapper<Exception> {
  /**
   * The Logger
   */
  private static final Logger LOG = LoggerFactory.getLogger(ExceptionXmlMapper.class);

  /**
   * Map the catched Exception to the response body (xml format)
   */
  @Override
  public Response toResponse(Exception arg0) {
    // On cree une instance de SAXBuilder
    final Element root = new Element("Exception");
    final Document doc = new Document(root);
    this.populateXML(arg0, root);
    final String res = new XMLOutputter(Format.getPrettyFormat()).outputString(doc);
    LOG.info("CXF exception thrown: " + res, arg0);
    return Response.status(Response.Status.FORBIDDEN)
        .type(MediaType.APPLICATION_XML)
        .header(ExceptionMappingConstants.EXCEPTION_BODY_HEADER, res).build();

  }

  /**
   * Create the xml description of an exception
   * 
   * @param arg0
   *          the Throwable
   * @param root
   *          the Xml Element (Jdom)
   */
  private void populateXML(Throwable arg0, Element root) {
    final Element clazz = new Element("class");
    clazz.setText(arg0.getClass().getSimpleName());
    root.getChildren().add(clazz);
    final Element message = new Element("message");
    message.setText(arg0.getMessage());
    root.getChildren().add(message);
    final Element localizedMessage = new Element("localizedMessage");
    localizedMessage.setText(arg0.getLocalizedMessage());
    root.getChildren().add(localizedMessage);
    if (arg0.getCause() != null) {
      final Element cause = new Element("Cause");
      root.getChildren().add(cause);
      this.populateXML(arg0.getCause(), cause);
    }
  }

}

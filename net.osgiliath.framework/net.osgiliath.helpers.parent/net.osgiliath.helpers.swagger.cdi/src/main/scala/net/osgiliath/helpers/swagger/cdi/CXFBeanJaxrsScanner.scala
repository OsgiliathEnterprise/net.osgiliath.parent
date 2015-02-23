package net.osgiliath.helpers.swagger.cdi;

/*
 * #%L
 * net.osgiliath.helpers.swagger.cdi
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

import scala.collection.JavaConverters._
import scala.collection.immutable.List
import com.google.common.base.Function
import com.google.common.collect.Collections2
import com.wordnik.swagger.config.Scanner
import com.wordnik.swagger.jaxrs.config.BeanConfig
import javax.ws.rs.core.Application
import javax.servlet.ServletConfig
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.ClasspathHelper
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.Reflections
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.core.SwaggerContext
import com.wordnik.swagger.reader.ClassReader
import com.wordnik.swagger.reader.ClassReaders
import org.slf4j.LoggerFactory
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader

class CXFBeanJaxrsScanner(classLoader: ClassLoader) extends BeanConfig {

  override def classesFromContext(app: Application, sc: ServletConfig): List[Class[_]] = {
    val config = new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(resourcePackage, classLoader)).setScanners(
      new TypeAnnotationsScanner(), new SubTypesScanner())
    config.addClassLoader(classLoader);
    new Reflections(config).getTypesAnnotatedWith(classOf[Api]).asScala.toList

  }

}

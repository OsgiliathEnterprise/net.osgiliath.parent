package mocks;

/*
 * #%L
 * net.osgiliath.hello.routes
 * %%
 * Copyright (C) 2013 Osgiliath
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

import java.util.List;

import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.business.spi.services.HelloService;
import net.osgiliath.hello.model.jpa.model.HelloEntity;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

//TODO Mock service

public class HelloCDIServiceMock implements HelloService {
  private List mockedMessageStrings = Lists.newArrayList();

  @Override
  public void persistHello(HelloEntity helloMessage_p) {
    mockedMessageStrings.add(helloMessage_p);
  }

  @Override
  public Hellos getHellos() {

    return Hellos
        .builder()
        .helloCollection(
            Lists.newArrayList(Collections2.transform(mockedMessageStrings,
                new Function<HelloEntity, String>() {

                  @Override
                  public String apply(HelloEntity arg0) {

                    return arg0.getHelloMessage();
                  }
                }))).build();
  }

  @Override
  public void deleteAll() {
    mockedMessageStrings.clear();
  }

}

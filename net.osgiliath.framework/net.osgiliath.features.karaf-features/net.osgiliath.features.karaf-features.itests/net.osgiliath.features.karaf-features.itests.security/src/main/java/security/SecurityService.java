package security;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.security
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

/**
 * Authenticate a {@link MUser}
 * 
 * @author Charlie
 * 
 */
public interface SecurityService {
    /**
     * Authenticate a {@link MUser},
     * 
     * @param username
     *            his username
     * @param password
     *            his password
     * @return true if the user exists
     */
    boolean authenticate(final String username, final String password);

    /**
     * Register the {@link MUser}
     * 
     * @param arg0
     *            the {@link MUser} to register
     * @return the registered {@link MUser}
     */
     MUser onSubscription(MUser arg0);
}

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
 * Right access for an {@link MUser}
 * 
 * @author Charlie
 * 
 */
public enum AUTHORITY {

    MEMBER("Member"), PREMIUM("Premium");
    public static final String S_MEMBER = "Member";
    public static final String S_PREMIUM = "Premium";

    private final String auth;

    /**
     * Constructor
     * 
     * @param auth
     *            the string for the authority
     */
    AUTHORITY(String auth) {
	this.auth = auth;
    }

    /**
     * return the string equivalent of the object
     * 
     * @return as it said
     */
    public final String getAuth() {
	return auth;
    }

}

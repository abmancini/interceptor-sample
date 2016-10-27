/**
 * Copyright (C) 2016 Red Hat, Inc. and/or its affiliates.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.e.pgwt.client.local;

import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.security.client.local.api.SecurityContext;
import org.jboss.errai.security.shared.api.Group;
import org.jboss.errai.security.shared.api.Role;
import org.jboss.errai.security.shared.api.RoleImpl;
import org.jboss.errai.security.shared.api.identity.User;
import org.jboss.errai.ui.nav.client.local.Navigation;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@EntryPoint
public class AppSetup {



    //simple hardcoded user
    public static class SimpleUser implements User {

        private Set<Role> roles = new HashSet();

        private Set<Group> groups = new HashSet<>();//unused
        private Map<String, String> properties = new HashMap<>();//unused


        public SimpleUser() {
            roles.add(new RoleImpl("ROLE000"));

        }



        @Override
        public String getIdentifier() {
            return "Username";
        }

        @Override
        public Set<Role> getRoles() {
            return roles;
        }

        @Override
        public Set<Group> getGroups() {
            return groups;
        }

        @Override
        public Map<String, String> getProperties() {
            return properties;
        }


        @Override
        public void setProperty(String name, String value) {
            properties.put(name, value);
        }

        @Override
        public void removeProperty(String name) {
            properties.remove(name);
        }

        @Override
        public String getProperty(String name) {
            return properties.get(name);
        }

    }




    @Inject
    Navigation navigation;

    @Inject
    SecurityContext securityContext;

    @AfterInitialization
    public void afterInitialization() {



        //show navpanel in the dom
        RootPanel.get().add(navigation.getContentPanel());


        //initialize fake security
        securityContext.setCachedUser( new SimpleUser() );




    }





}

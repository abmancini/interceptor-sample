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

import com.google.gwt.user.client.ui.HTML;
import org.jboss.errai.security.client.local.api.SecurityContext;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.nav.client.local.api.LoginPage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Page(path = "/login", role = LoginPage.class)
public class LoginPageImpl extends HTML {

    /*
        questa pagina viene usata per gestire il loading della prima pagina
        per via del caching come suggerito nella documentazione di errai:
        http://docs.jboss.org/errai/4.0.0.Beta1/errai/reference/html_single/#sid-21758082
        13.3.4.1. Page Redirection and Caching
     */


    @Inject
    private SecurityContext securityContext;


    @PostConstruct
    private void setup() {
        setHTML("QUESTO MESSAGGIO NON DOVREBBE MAI ESSERE VISTO");
        //XXX aggungere messaggio di errore decente !!
    }


    @PageShowing
    public void checkForPendingCache() {


        // Check if cache is invalid.
        if (!securityContext.isUserCacheValid()) {
            //should not happen but ... we try
            //initialize errai-security
            securityContext.setCachedUser(new it.e.pgwt.client.local.AppSetup.SimpleUser());
        }

        //XXX
        //controllare se c'e' stato qualche errore !!
        securityContext.navigateBackOrHome();

    }

}
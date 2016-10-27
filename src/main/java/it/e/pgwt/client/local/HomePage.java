/**
 * Copyright (C) 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.e.pgwt.client.local;

import com.google.gwt.dom.client.Style;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import it.e.pgwt.client.shared.Customer;
import it.e.pgwt.client.shared.CustomerService;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.enterprise.client.jaxrs.api.ResponseCallback;
import org.jboss.errai.enterprise.client.jaxrs.api.ResponseException;
import org.jboss.errai.enterprise.client.jaxrs.api.RestClient;
import org.jboss.errai.security.shared.api.annotation.RestrictedAccess;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShowing;

import javax.inject.Inject;

@RestrictedAccess(roles = {"ROLE000"})
@Page(path = "/home", role = DefaultPage.class)
public class HomePage extends VerticalPanel {


    @Inject
    private Caller<CustomerService> customerService;


  @PageShowing
  private void pageShowing() {

      HTML header = new HTML();
      header.setText("Sample");
      header.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);
      add(header);

      HTML desc = new HTML();
      desc.setHTML("The service is annotated with @RestricedAccess. <br> "  +
                  " the call with RemoteCallback works as expected <br>" +
                  " the call with ResponseCallback produces ClassCastException in the browser console <br>" +
                  "  Removing @RestricedAccess in the service let both calls to work"
      );
      desc.getElement().getStyle().setPadding( 10, Style.Unit.PX);

      add(desc);





      ResponseCallback responseCallback = new ResponseCallback() {
          @Override
          public void callback(Response response) {
              String s = "Response Text: " + response.getStatusText() + " Response code: "+ response.getStatusCode();
              add(new HTML(s) );
              Window.alert(s);
          }
      };

      RemoteCallback<Customer> remoteCallback = new RemoteCallback<Customer>() {
          @Override
          public void callback(Customer customer) {
              String s = " Response: " + customer.getNome();
              add( new HTML(s));
              Window.alert(s);
          }
      };



      ErrorCallback<?> errorCallback = (m, throwable) -> {


          String responseString;
          try {
              throw throwable;
          } catch (ResponseException e) {
              Response response = e.getResponse();
              responseString = "Response: " + response.getStatusText() + " (code: " + response.getStatusCode() + ")";
          } catch (Throwable t) {
              responseString = "Unexpected error: " + t.getMessage();
          }

          Window.alert("Error during request" + responseString);

          return false;
      };


      {
          Anchor btn = new Anchor("Make Request (using RemoteCallback)");

          btn.addClickHandler(
                  (e) -> {
                      customerService.call(remoteCallback, errorCallback).retrieveCustomerById(12);
                  }
          );

          add(btn);
      }
      {


          Anchor btn = new Anchor("Make Request (using ResponseCallback)");

          btn.addClickHandler(
                  (e) -> {
                      customerService.call(responseCallback, errorCallback).retrieveCustomerById(12);
                  }
          );

          add(btn);
      }
//      {
//          Anchor btn = new Anchor("Make Request (should fail serverside) ");
//
//          btn.addClickHandler(
//                  (e) -> {
//                      customerService.call(remoteCallback, errorCallback).retrieveCustomerById(0); //0 means fail !!
//                  }
//          );
//
//          add(btn);
//
//      }
//
//      {
//          Anchor btn = new Anchor("Make Request using ResponseCallback (should fail serverside) ");
//
//          btn.addClickHandler(
//                  (e) -> {
//                      customerService.call(responseCallback , errorCallback).retrieveCustomerById(0); //0 means fail !!
//                  }
//          );
//
//          add(btn);
//
//      }







  }

 }

package com.tp.webtp.resolver;

import org.springframework.oxm.Marshaller;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.Locale;

public class XmlViewResolver implements ViewResolver {
    private Marshaller marshaller;

    public XmlViewResolver(Marshaller marshaller) {
        this.marshaller = marshaller;
    }


        public View resolveViewName(String viewName, Locale locale) throws Exception {
            MarshallingView marshallingView = new MarshallingView();
            marshallingView.setMarshaller(marshaller);
            return marshallingView;
        }
    }


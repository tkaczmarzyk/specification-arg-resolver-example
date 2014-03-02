package net.kaczmarzyk.spring.data.jpa.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class SpecificationArgumentResolver implements HandlerMethodArgumentResolver {

    private List<HandlerMethodArgumentResolver> delegates = Arrays.asList(new SimpleSpecificationResolver(),
            new ConjunctionSpecificationResolver(), new DisjunctionSpecificationResolver());
    
    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mav, NativeWebRequest req,
            WebDataBinderFactory bider) throws Exception {
        
        for (HandlerMethodArgumentResolver delegate : delegates) {
            if (delegate.supportsParameter(param)) {
                return delegate.resolveArgument(param, mav, req, bider);
            }
        }
        
        return null;
    }

    @Override
    public boolean supportsParameter(MethodParameter param) {
        for (HandlerMethodArgumentResolver delegate : delegates) {
            if (delegate.supportsParameter(param)) {
                return true;
            }
        }
        return false;
    }

}

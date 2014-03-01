package net.kaczmarzyk.spring.data.jpa.web;

import java.util.ArrayList;
import java.util.Collection;

import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class SpecificationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mav, NativeWebRequest req,
            WebDataBinderFactory bider) throws Exception {
        
        Specification<?> spec = null;
        
        if (param.hasParameterAnnotation(Spec.class)) {
            spec = buildSpecification(param.getParameterAnnotation(Spec.class), req);
        }
        
        return spec;
    }

    private Specification<?> buildSpecification(Spec def, NativeWebRequest req) {
        try {
            Collection<String> args = new ArrayList<String>();
            for (String param : def.params()) {
                String paramValue = req.getParameter(param);
                if (paramValue != null) {
                    args.add(paramValue);
                }
            }
            
            String path = def.path();
            if (StringUtils.isEmpty(path)) {
                path = def.params()[0];
            }
            
            Specification<?> spec = def.spec().getConstructor(String.class, String[].class)
                    .newInstance(path, args.toArray(new String[args.size()]));
            
            return spec;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.getParameterType() == Specification.class
                && (param.hasParameterAnnotation(Spec.class) || param.hasParameterAnnotation(Or.class) || param
                        .hasParameterAnnotation(And.class));
    }

}

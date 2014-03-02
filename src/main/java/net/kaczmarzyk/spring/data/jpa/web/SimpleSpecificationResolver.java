package net.kaczmarzyk.spring.data.jpa.web;

import java.util.ArrayList;
import java.util.Collection;

import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

class SimpleSpecificationResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mav, NativeWebRequest req,
            WebDataBinderFactory binderFactory) throws Exception {
        
        Spec def = param.getParameterAnnotation(Spec.class);
        
        return buildSpecification(req, def);
    }

    Specification<Object> buildSpecification(NativeWebRequest req, Spec def) {
        try {
            Collection<String> args = new ArrayList<String>();
            for (String webParam : def.params()) {
                String paramValue = req.getParameter(webParam);
                if (paramValue != null) {
                    args.add(paramValue);
                }
            }
            
            String path = def.path();
            if (StringUtils.isEmpty(path)) {
                path = def.params()[0];
            }
            
            @SuppressWarnings("unchecked")
            Specification<Object> spec = def.spec().getConstructor(String.class, String[].class)
                    .newInstance(path, args.toArray(new String[args.size()]));
            
            return spec;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    boolean canBuildSpecification(NativeWebRequest req, Spec def) {
        for (String param : def.params()) {
            if (req.getParameter(param) == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.getParameterType() == Specification.class && param.hasParameterAnnotation(Spec.class);
    }

}

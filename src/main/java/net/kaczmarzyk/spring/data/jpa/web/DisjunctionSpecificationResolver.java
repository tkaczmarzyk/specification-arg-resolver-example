package net.kaczmarzyk.spring.data.jpa.web;

import java.util.ArrayList;
import java.util.List;

import net.kaczmarzyk.spring.data.jpa.domain.Disjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class DisjunctionSpecificationResolver implements HandlerMethodArgumentResolver {

    private SimpleSpecificationResolver specResolver = new SimpleSpecificationResolver();
    
    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.getParameterType() == Specification.class && param.hasParameterAnnotation(Or.class);
    }

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Or def = param.getParameterAnnotation(Or.class);
        
        List<Specification<Object>> innerSpecs = new ArrayList<Specification<Object>>();
        for (Spec innerDef : def.value()) {
            if (specResolver.canBuildSpecification(webRequest, innerDef)) {
                innerSpecs.add(specResolver.buildSpecification(webRequest, innerDef));
            }
        }
        
        return new Disjunction<Object>(innerSpecs);
    }

}

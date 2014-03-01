package net.kaczmarzyk.spring.data.jpa.web;

import java.util.ArrayList;
import java.util.List;

import net.kaczmarzyk.spring.data.jpa.domain.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


class ConjunctionSpecificationResolver implements HandlerMethodArgumentResolver {

    private SimpleSpecificationResolver specResolver = new SimpleSpecificationResolver();
    
    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.getParameterType() == Specification.class && param.hasParameterAnnotation(And.class);
    }

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        And def = param.getParameterAnnotation(And.class);
        
        List<Specification<Object>> innerSpecs = new ArrayList<Specification<Object>>();
        for (Spec innerDef : def.value()) {
            try {
                innerSpecs.add(specResolver.buildSpecification(webRequest, innerDef));
            } catch (IllegalStateException e) {
                // ignore
            }
        }
        
        return new Conjunction<Object>(innerSpecs);
    }

}

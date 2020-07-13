package ua.ivan909020.api.annotations;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "size", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", allowMultiple = true)
})
public @interface ApiPageable {

}

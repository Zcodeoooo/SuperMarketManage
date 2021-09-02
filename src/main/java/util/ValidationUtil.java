package util;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtil {
    private static Validator validator = Validation.byProvider(HibernateValidator.class)
            .configure()
            /* 有一个失败就停止检查 */
            .failFast(true)
            .buildValidatorFactory()
            .getValidator();
    public static List<String> valid(Object obj){
        Set<ConstraintViolation<Object>> validate = validator.validate(obj);
        List<String> collect = validate.stream().map(v -> "属性" + v.getPropertyPath() + "属性的值" + v.getInvalidValue() + "不通过的信息" + v.getMessage()).collect(Collectors.toList());
        return collect;
    }


}

package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
// import javax.faces.convert.FacesConverter;

import java.math.BigDecimal;

// @FacesConverter("bigDecimalConverter")
public class BigDecimalConverter implements Converter<BigDecimal> {

    @Override
    public BigDecimal getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        value = value.replace(",", ".");

        return new BigDecimal(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, BigDecimal value) {
        if (value == null) return "";
        return value.stripTrailingZeros().toPlainString();
    }
}

package taco_proj.taco_cloud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Document(collection="orders")
public class TacoOrder implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private Date placedAt = new Date();
    
    // checking validate (not null)
    @NotBlank(message="Delivery name is required")
    private String deliveryName;

    @NotBlank(message="Street is required")
    private String deliveryStreet;

    @NotBlank(message="City is required")
    private String deliveryCity;

    @NotBlank(message="State is required")
    @Pattern(regexp="^([a-zA-Z]{2})$", message="Must exist 2 charakter")
    private String deliveryState;

    @NotBlank(message="ZIP-code is required")
    private String deliveryZip;

    // checking validate (luhn-algoritm)
    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;
    // checking validate (regexp)
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message="Must be formatted MM/YY")
    private String ccExpiration;
    // checking validate (3 int without fractional part)
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }
}

package com.ouhamza.ecommerce.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
//	@Column(name = "pic_byte")
//    private byte[] picByte;
//    
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "price")
    private Float price;

	@Column(name = "quantity")
    private int quantity;
    
	@Column(name = "grocery_id")
    private Long groceryId;

	@ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
	
	


}

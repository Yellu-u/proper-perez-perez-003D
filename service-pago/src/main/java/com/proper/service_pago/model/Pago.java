package com.proper.service_pago.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long pagoId;
        private Double monto;
        private String metodoPago; //ej: transferencia, cheque
        private String estadoPago; //ej: pendiente, pagado o rechazado
        private LocalDate fechaPago;

        private Long pedidoId;

        @Transient
        private Object pedido;
}

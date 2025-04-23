package kr.hhplus.be.server.infra.inventory.jpa;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.inventory.Inventory;
import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.infra.point.jpa.PointEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "reserved_quantity")
    private long reservedQuantity;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static InventoryEntity toEntity(Inventory inventory) {
        return InventoryEntity.builder()
                .inventoryId(inventory.inventoryId())
                .productId(inventory.productId())
                .quantity(inventory.quantity())
                .reservedQuantity(inventory.reservedQuantity())
                .updatedAt(inventory.updatedAt())
                .build();
    }

    public static Inventory toDomain (InventoryEntity entity){
        return Inventory.builder()
                .inventoryId(entity.getInventoryId())
                .productId(entity.getProductId())
                .quantity(entity.getInventoryId())
                .reservedQuantity(entity.getReservedQuantity())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}

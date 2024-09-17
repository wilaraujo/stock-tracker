``` mermaid
classDiagram
	class Product {
		-Integer id
		-String name
		-String sku
		-Stsring description
		-BigDecimal price
		-String barcodes
		-String imgUrl
		-ProductCategory category
		-UnitOfMeasurement baseUnit
	    -PackagingConfiguration packagingConfiguration
	}
	
	class Batch {
	    -Integer id
	    -String code
	    -Product product
	    -Instant productionDate
	    -Instant expirationDate
		-String barcode
	    -int quantity
	    -String status
	}
	
	class ProductCategory {
		-Integer id
		-String name
		-ProductCategory parentCategory
	}
	
	class Location {
		-Integer id
		-String description
	}
	
	class StockLevel {
	    -Integer id
	    -Product product
	    -Location location
	    -int quantity
	    -int minimumStockLevel
	    -int maximumStockLevel
	    -Instant lastUpdated
	}

	class StockItem {
	    -Integer id
	    -Product product
	    -Batch batch
	    -Location location
	    -int quantity
	    -Instant lastUpdated
	    -UnitOfMeasurement currentUnit
	}

	class MovementRequest {
	    -Integer id
	    -Instant requestDate
	    -String status
	    -User initiatedBy
	}
	
	class MovementDetail {
	    -Integer id
	    -MovementRequest movementRequest
	    -StockItem stockItem
	    -Location fromLocation
	    -Location toLocation
	    -int quantityMoved
	    -Instant movementDate
	}

	class StockAdjustment {
	    -Integer id
	    -StockItem stockItem
	    -Product product
		-Batch batch
	    -Location location
	    -int originalQuantity
	    -int adjustedQuantity
	    -String reason
	    -Instant adjustmentDate
	    -User adjustedBy
	}

	class User {
	    -Integer id
	    -String name
	    -String role
	}

	class UnitOfMeasurement {
	    -int id
	    -String name
	    -String abbreviation
	    -double conversionFactorToBase
	}
	
	class PackagingConfiguration {
	    -int id
	    -int quantityInPackage
	    -int quantityInCase
	    -UnitOfMeasurement packageUnit
	    -UnitOfMeasurement caseUnit
	}

Product "1" *-- Batch
Product "1..*" --o ProductCategory
ProductCategory -- ProductCategory
StockLevel -- Product 
StockLevel -- Location
MovementRequest -- MovementDetail
MovementRequest -- User
MovementDetail -- Location


```

https://chatgpt.com/share/66e867d5-d610-8005-97e4-54ffd68e5433

### Location
Represents specific storage areas within a warehouse, such as bins, shelves, or sections. The Location entity helps manage where stock items are placed within the warehouse.

###  User
Represents a person interacting with the system, such as a warehouse worker or manager. The `User` entity is linked to actions they initiate or complete, such as creating a movement request.

### AuditLog
>[!note]
>Not implemented
- **Attributes**:
    - `logID`
    - `timestamp`
    - `actionType`
    - `performedBy` (Reference to `User`)
    - `description`
- Tracks all actions within the system for accountability, such as when and who initiated or completed a movement.

### **Example of Interaction**

- **Scenario**: A warehouse worker (User) submits a `MovementRequest` to move 50 units of a `StockItem` from one `Location` to another within the same `Warehouse`.
    - The `MovementRequest` is created with a `status` of "Pending."
    - The `MovementDetail` entity captures the specifics of the movement, such as the `StockItem` involved, the `fromLocation`, and the `toLocation`.
    - Once the movement is completed, the `InventoryManagementSystem` updates the `StockItem`'s `currentLocation` and the `MovementRequest` status to "Completed."
    - An `AuditLog` entry is created to record the action.

# Stock Levels

### Key Concepts with Examples

#### 1. **Current Stock Level**

- **Example:**  
    You have 50 units of a particular product in the warehouse. This is your current stock level for that product.

#### 2. **Minimum Stock Level**

- **Example:**  
    You set a minimum stock level of 10 units for the "Laptop". When stock drops below this level, it's a signal to reorder.

#### 3. **Maximum Stock Level**

- **Example:**  
    You set a maximum stock level of 100 units for the "Laptop". This helps avoid overstocking and ensures optimal use of warehouse space.

#### 4. **Reorder Point**

- **Example:**  
    If the stock level for "Laptop" falls to 15 units, a reorder is triggered to maintain sufficient stock. This level is set based on lead times and demand.

#### 5. **Safety Stock**

- **Example:**  
    You keep an extra 5 units of "Laptop" as safety stock to account for unforeseen demand or delays in replenishment.

### How it Works Together

1. **Monitoring Stock Levels:**  
    The system regularly checks the stock level of each product. For example, it notices that the "Laptop" stock has fallen to 12 units.
    
2. **Triggering Reorder:**  
    Since the stock level (12 units) is below the reorder point (15 units), the system triggers a reorder to replenish stock.
    
3. **Updating Stock Levels:**  
    Once new stock arrives, the system updates the stock level to reflect the new quantity. If 30 more units are received, the stock level for "Laptop" becomes 42 units.
    

### Conclusion

Managing **Stock Levels** ensures that you have the right amount of inventory to meet demand without overstocking. It involves tracking how much of each product is available, knowing when to reorder, and setting thresholds to maintain efficient operations.

## Stock Adjustment

**Stock Adjustment** refers to the process of modifying the recorded inventory levels in a warehouse or storage system to accurately reflect the actual quantity of stock on hand. This is often necessary when there are discrepancies between the recorded inventory and the physical count of items. Stock adjustments can be due to various reasons, such as errors in counting, damage to goods, or losses due to theft.
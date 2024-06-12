#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TABLE_SIZE 101
#define MAX_DESCRIPTION 100
#define MAX_PRODUCTS 100

typedef struct {
    int code;
    int quantity;
    float price;
    char description[MAX_DESCRIPTION];
} Product;

typedef struct {
    int product_code;
    int status; // 0 - free, 1 - in use
    int next; // Internal chaining
    int file_position;
} HashRecord;

int hashFunction(int code) {
    return code % TABLE_SIZE;
}

HashRecord searchRecord(HashRecord hash_table[], int code) {
    int index = hashFunction(code);
  
    while (hash_table[index].status != 0 && hash_table[index].product_code != code && hash_table[index].next != -1) {
        index = hash_table[index].next;
    }
  
    return hash_table[index];
}

void listProducts(Product products[], int size) {
    for (int i = 0; i < size; i++) {
      
        if (products[i].code != 0) {
            displayProduct(products[i]);
            printf("\n");
        }
    }
}

void displayProduct(Product product) {
    printf("Code: %d\n", product.code);
    printf("Description: %s\n", product.description);
    printf("Price: %.2f\n", product.price);
    printf("Quantity in stock: %d\n", product.quantity);
}

void registerProduct(Product products[], HashRecord hash_table[]) {
    Product new_product;
    HashRecord new_record;
  
    printf("Enter the product code: ");
    scanf("%d", &new_product.code);
    printf("Enter the product description: ");
    scanf(" %[^\n]", new_product.description);
    printf("Enter the product price: ");
    scanf("%f", &new_product.price);
    printf("Enter the quantity in stock: ");
    scanf("%d", &new_product.quantity);

    int index = hashFunction(new_product.code);
  
    while (hash_table[index].status != 0 && hash_table[index].next != -1) {
        index = hash_table[index].next;
    }

    new_record.product_code = new_product.code;
    new_record.file_position = index;
    new_record.status = 1;
    new_record.next = -1;
    hash_table[index] = new_record;
    products[index] = new_product;

    printf("Product registered successfully.\n");
}

void editProduct(Product products[], int index) { //Product found
    printf("Enter the new product description: ");
    scanf(" %[^\n]", products[index].description);
    printf("Enter the new product price: ");
    scanf("%f", &products[index].price);
    printf("Enter the new quantity in stock: ");
    scanf("%d", &products[index].quantity);
    printf("Product edited successfully.\n");
}

int main() {
    Product products[MAX_PRODUCTS];
    HashRecord hash_table[TABLE_SIZE];
    
    for (int i = 0; i < TABLE_SIZE; i++) {
        hash_table[i].product_code = 0;
        hash_table[i].file_position = -1;
        hash_table[i].status = 0;
        hash_table[i].next = -1;
    }

    int choose;
  
    do {
        printf("\nMenu:\n");
        printf("1. Register a new product\n");
        printf("2. List all products\n");
        printf("3. Edit data of a product\n");
        printf("4. Display data of a product\n");
        printf("0. Exit\n");
        printf("Choose an option: ");
        scanf("%d", &choose);

        switch (choose) {
            case 1:
                registerProduct(products, hash_table);
                break;

            case 2:
                listProducts(products, MAX_PRODUCTS);
                break;

            case 3:
                printf("Enter the product code you want to edit: ");
                scanf("%d", &code);
                record = searchRecord(hash_table, code);
                
                if (record.status == 0 || record.product_code != code) {
                    printf("Product not found.\n");
                } else {
                    editProduct(products, record.file_position);
                }
                break;
          
            case 4:
                int code;
                printf("Enter the product code: ");
                scanf("%d", &code);
                HashRecord record = searchRecord(hash_table, code);
                
              if (record.status == 0 || record.product_code != code) {
                    printf("Product not found.\n");
                } else {
                    displayProduct(products[record.file_position]);
                }
                break;

            case 0:
                printf("Exit the program.\n");
                break;

            default:
                printf("Invalid option.\n");
        }
      
    } while (choose != 0);

    return 0;
}

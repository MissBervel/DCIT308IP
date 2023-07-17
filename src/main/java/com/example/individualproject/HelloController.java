package com.example.individualproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class HelloController {
    private Connection connection;

    //Database Credentials
    String url = "jdbc:mysql://localhost:3306/benedicta";
    String username = "root";
    String password = "kukuA123@";
    @FXML
    public AnchorPane addGoods_view;
    @FXML
    public AnchorPane viewVendors_view;
    @FXML
    public AnchorPane viewGoods_view;
    @FXML
    public AnchorPane viewBills_view;
    @FXML
    public AnchorPane issuedGoods_view;
    @FXML
    public AnchorPane viewIssuedGoods_view;
    @FXML
    public Button addGoods_btn;
    @FXML
    public Button viewVendors_btn;
    @FXML
    public Button viewGoods_btn;
    @FXML
    public Button viewBills_btn;
    @FXML
    public Button issuedGoods_btn;
    @FXML
    public Button viewIssuedGoods_btn;

    @FXML
    public Button addProducts_btn;

    @FXML
    public Button removeProduct_btn;
    public TextField goodsName_input;
    public TextField categoryName_input;
    public TextField quantity_input;
    public TextField unitPrice_input;

    private HashMap<Integer, String> categoryMap;
    private HashMap<Integer, Stacks<Goods>> categoryStacks;
    private HashMap<Integer, Queues<Goods>> categoryQueues;
    private HashMap<Integer, Lists<Goods>> categoryLists;

    private PreparedStatement prepare;
    private Connection connect;
    private ResultSet result;
    private Statement statement;

    @FXML
    private TableView<DBGoods> viewGoods_tableView;
    @FXML
    private TableColumn<DBGoods, String> goodsId_col;
    @FXML
    private TableColumn<DBGoods, String> goodsName_col;
    @FXML
    private TableColumn<DBGoods, String> categoryId_col;
    @FXML
    private TableColumn<DBGoods, String> quantity_col;
    @FXML
    private TableColumn<DBGoods, String> unitPrice_col;

    @FXML
    private TableView<DBVendors> vendors_table;
    @FXML
    private TableColumn<DBVendors, String> vendorId_col;
    @FXML
    private TableColumn<DBVendors, String> vendorName_col;
    @FXML
    private TableColumn<DBVendors, String> contact_col;
    @FXML
    public Button addVendorsView_btn;

    public AnchorPane addVendor_view;
    public TextField vendorName_input;
    public TextField vendorTel_input;
    public Button addVendor_btn;

    //Constructor
    public HelloController() {
        categoryMap = new HashMap<>();
        categoryStacks = new HashMap<>();
        categoryQueues = new HashMap<>();
        categoryLists = new HashMap<>();
        initializeCategoryMap();
    }


    // Additional methods for initializing categoryMap, checking if a category exists, and connecting to the database
    private void initializeCategoryMap() {
        categoryMap.put(1, "Beverages");
        categoryMap.put(2, "Bread");
        categoryMap.put(3, "Canned");
        categoryMap.put(4, "Dairy");
        categoryMap.put(5, "Dry");
        categoryMap.put(6, "Frozen");
        categoryMap.put(7, "Meat");
        categoryMap.put(8, "Farm produce");
        categoryMap.put(9, "Home cleaners");
        categoryMap.put(10, "Paper");
        categoryMap.put(11, "Home care");
    }

    public void showAddGoodsView(ActionEvent event) {
        if(event.getSource() == addGoods_btn){
            addGoods_view.setVisible(true);
            viewVendors_view.setVisible(false);
            viewGoods_view.setVisible(false);
            viewBills_view.setVisible(false);
            issuedGoods_view.setVisible(false);
            viewIssuedGoods_view.setVisible(false);
            addVendor_view.setVisible(false);
        }
    }

    public void showViewVendorsView(ActionEvent event) {
        if(event.getSource() == viewVendors_btn){
            addGoods_view.setVisible(false);
            viewVendors_view.setVisible(true);
            viewGoods_view.setVisible(false);
            viewBills_view.setVisible(false);
            issuedGoods_view.setVisible(false);
            viewIssuedGoods_view.setVisible(false);
            addVendor_view.setVisible(false);

            addVendorsShowListData();
        }
    }

    public void showViewGoodsView(ActionEvent event) {
        if(event.getSource() == viewGoods_btn){
            addGoods_view.setVisible(false);
            viewVendors_view.setVisible(false);
            viewGoods_view.setVisible(true);
            viewBills_view.setVisible(false);
            issuedGoods_view.setVisible(false);
            viewIssuedGoods_view.setVisible(false);
            addVendor_view.setVisible(false);

            addGoodsShowListData();
        }
    }

    public ObservableList<DBVendors> addVendorsListData(){
        ObservableList<DBVendors> VendorList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM vendors";
        connectToDatabase(url, username, password);

        try{
            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();
            DBVendors vendorD;

            while(result.next()){
                vendorD = new DBVendors(result.getInt("vendor_id")
                        , result.getString("vendor_name")
                        , result.getString("contact_info"));
                VendorList.add(vendorD);

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return VendorList;
    }

    private ObservableList<DBVendors> addVendorsList;
    public void addVendorsShowListData(){
        addVendorsList = addVendorsListData();

        vendorId_col.setCellValueFactory(new PropertyValueFactory<>("vendorID"));
        vendorName_col.setCellValueFactory(new PropertyValueFactory<>("vendorName"));
        contact_col.setCellValueFactory(new PropertyValueFactory<>("contact"));
        vendors_table.setItems(addVendorsList);
    }

    public ObservableList<DBGoods> addGoodsListData(){
        ObservableList<DBGoods> GoodsList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM products";
        connectToDatabase(url, username, password);

        try{
            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();
            DBGoods prodD;

            while(result.next()){
                prodD = new DBGoods(result.getInt("product_id")
                        , result.getString("product_name")
                        , result.getInt("category_id"),result.getInt("unitPrice")
                        , result.getInt("quantity"));
                GoodsList.add(prodD);

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return GoodsList;
    }

    private ObservableList<DBGoods> addGoodsList;
    public void addGoodsShowListData(){
        addGoodsList = addGoodsListData();

        goodsId_col.setCellValueFactory(new PropertyValueFactory<>("productID"));
        goodsName_col.setCellValueFactory(new PropertyValueFactory<>("productName"));
        categoryId_col.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        unitPrice_col.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        viewGoods_tableView.setItems(addGoodsList);
    }

    public void showviewBillsView(ActionEvent event) {
        if(event.getSource() == viewBills_btn){
            addGoods_view.setVisible(false);
            viewVendors_view.setVisible(false);
            viewGoods_view.setVisible(false);
            viewBills_view.setVisible(true);
            issuedGoods_view.setVisible(false);
            viewIssuedGoods_view.setVisible(false);
            addVendor_view.setVisible(false);
        }
    }

    public void showIssuedGoodsView(ActionEvent event) {
        if(event.getSource() == issuedGoods_btn){
            addGoods_view.setVisible(false);
            viewVendors_view.setVisible(false);
            viewGoods_view.setVisible(false);
            viewBills_view.setVisible(false);
            issuedGoods_view.setVisible(true);
            viewIssuedGoods_view.setVisible(false);
            addVendor_view.setVisible(false);
        }
    }

    public void showViewIssuedGoodsView(ActionEvent event) {
        if(event.getSource() == viewIssuedGoods_btn){
            addGoods_view.setVisible(false);
            viewVendors_view.setVisible(false);
            viewGoods_view.setVisible(false);
            viewBills_view.setVisible(false);
            issuedGoods_view.setVisible(false);
            viewIssuedGoods_view.setVisible(true);
            addVendor_view.setVisible(false);
        }
    }

    public void showAddVendorsView(ActionEvent event) {
        if(event.getSource() == addVendorsView_btn){
            addGoods_view.setVisible(false);
            viewVendors_view.setVisible(false);
            viewGoods_view.setVisible(false);
            viewBills_view.setVisible(false);
            issuedGoods_view.setVisible(false);
            viewIssuedGoods_view.setVisible(false);
            addVendor_view.setVisible(true);
        }
    }

    //Method to add vendors to the system
    public void addVendors(ActionEvent event) throws SQLException {
        if(event.getSource() == addVendor_btn) {
            String vendorName = vendorName_input.getText();
            String contact = vendorTel_input.getText();

            Vendors vendors = new Vendors(vendorName, contact);
            int vendorID = vendors.getVendor_id();

            connectToDatabase(url, username, password);
            if(connection != null) {
                String sql = "INSERT INTO vendors (vendor_id, vendor_name, contact_info) VALUES (?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, vendorID);
                stmt.setString(2, vendorName);
                stmt.setString(3, contact);
                stmt.executeUpdate();
                stmt.close();
            }
            connection.close();
        }
    }

    //Method to add goods to the system
    public void addGoods(ActionEvent event) throws SQLException {
        if (event.getSource() == addProducts_btn) {
            // Take input from text fields
            String goodsName = goodsName_input.getText();
            String category = categoryName_input.getText();
            int quantity = Integer.parseInt(quantity_input.getText());
            int unitPrice = Integer.parseInt(unitPrice_input.getText());

            int categoryId = -1;  // Default value if category is not found

            // Check if the category exists in the categoryMap
            for (Map.Entry<Integer, String> entry : categoryMap.entrySet()) {
                if (entry.getValue().equalsIgnoreCase(category)) {
                    categoryId = entry.getKey();
                    break;
                }
            }

            if (categoryId != -1) {
                Category categories = new Category(category);
                Goods goods = new Goods(goodsName, quantity, unitPrice);

                if (categoryId >= 1 && categoryId <= 4) {
                    Stacks<Goods> stack = categoryStacks.get(categoryId);
                    if (stack == null) {
                        stack = new Stacks<>();
                        categoryStacks.put(categoryId, stack);
                    }
                    stack.push(goods);
                } else if (categoryId >= 5 && categoryId <= 7) {
                    Queues<Goods> queue = categoryQueues.get(categoryId);
                    if (queue == null) {
                        queue = new Queues<>();
                        categoryQueues.put(categoryId, queue);
                    }
                    queue.enqueue(goods);
                } else if (categoryId >= 8 && categoryId <= 11) {
                    Lists<Goods> list = categoryLists.get(categoryId);
                    if (list == null) {
                        list = new Lists<>();
                        categoryLists.put(categoryId, list);
                    }
                    list.add(goods);
                }

                // Add goods to the database
                connectToDatabase(url, username, password);
                if (connection != null) {
                    // Insert product data into products table
                    String sql = "INSERT INTO products (product_id, product_name, category_id, quantity, unit_price) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setInt(1, goods.getGoods_id());
                    stmt.setString(2, goodsName);
                    stmt.setInt(3, categoryId);
                    stmt.setInt(4, quantity);
                    stmt.setInt(5, unitPrice);
                    stmt.executeUpdate();
                    stmt.close();

                    // Insert category data into categories table if it's a new category
                    if (!isCategoryExists(categoryId)) {
                        sql = "INSERT INTO categories (category_id, category_name) VALUES (?, ?)";
                        stmt = connection.prepareStatement(sql);
                        stmt.setInt(1, categoryId);
                        stmt.setString(2, category);
                        stmt.executeUpdate();
                        stmt.close();
                    }

                    connection.close();
                }

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Goods Added");
                alert.setHeaderText(null);
                alert.setContentText("Goods added successfully!");
                alert.showAndWait();
            } else {
                // Show error message for invalid category name
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Category");
                alert.setHeaderText(null);
                alert.setContentText("Invalid category name: " + category);
                alert.showAndWait();
            }
        }
    }


    private boolean isCategoryExists(int categoryId) throws SQLException {
        connectToDatabase(url, username, password);
        if (connection != null) {
            String sql = "SELECT COUNT(*) FROM category WHERE category_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            stmt.close();
            connection.close();
            return count > 0;
        }
        return false;
    }

    //Method to connect to the database
    public void connectToDatabase(String url, String username, String password){
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database Connected Successfully");
        } catch (SQLException error) {
            System.out.println(error);
        }
    }

    //Method to remove goods from the system
    public void removeGoods(ActionEvent event) throws SQLException {
        if (event.getSource() == removeProduct_btn) {
            // Take input from text fields
            String goodsName = goodsName_input.getText();
            String category = categoryName_input.getText();

            int categoryId = -1;  // Default value if category is not found

            // Check if the category exists in the categoryMap
            for (Map.Entry<Integer, String> entry : categoryMap.entrySet()) {
                if (entry.getValue().equalsIgnoreCase(category)) {
                    categoryId = entry.getKey();
                    break;
                }
            }

            if (categoryId != -1) {
                if (categoryId >= 1 && categoryId <= 4) {
                    Stacks<Goods> stack = categoryStacks.get(categoryId);
                    if (stack != null && !stack.isEmpty()) {
                        Goods removedGoods = stack.pop();
                        deleteProductFromDatabase(removedGoods.getGoods_id());
                        // Rest of the code for handling removed product from stack
                    } else {
                        // Show error message for empty stack
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Empty Stack");
                        alert.setHeaderText(null);
                        alert.setContentText("The stack is empty for the specified category.");
                        alert.showAndWait();
                    }
                } else if (categoryId >= 5 && categoryId <= 7) {
                    Queues<Goods> queue = categoryQueues.get(categoryId);
                    if (queue != null && !queue.isEmpty()) {
                        Goods removedGoods = queue.dequeue();
                        deleteProductFromDatabase(removedGoods.getGoods_id());
                        // Rest of the code for handling removed product from queue
                    } else {
                        // Show error message for empty queue
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Empty Queue");
                        alert.setHeaderText(null);
                        alert.setContentText("The queue is empty for the specified category.");
                        alert.showAndWait();
                    }
                } else if (categoryId >= 8 && categoryId <= 11) {
                    Lists<Goods> list = categoryLists.get(categoryId);
                    if (list != null && !list.isEmpty()) {
                        int index = -1;
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getGoods_name().equalsIgnoreCase(goodsName)) {
                                index = i;
                                break;
                            }
                        }
                        if (index != -1) {
                            Goods removedGoods = list.remove(index);
                            deleteProductFromDatabase(removedGoods.getGoods_id());
                            // Rest of the code for handling removed product from list
                        } else {
                            // Show error message for product not found in list
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Product Not Found");
                            alert.setHeaderText(null);
                            alert.setContentText("The product does not exist in the list for the specified category.");
                            alert.showAndWait();
                        }
                    } else {
                        // Show error message for empty list
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Empty List");
                        alert.setHeaderText(null);
                        alert.setContentText("The list is empty for the specified category.");
                        alert.showAndWait();
                    }
                }
            } else {
                // Show error message for invalid category name
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Category");
                alert.setHeaderText(null);
                alert.setContentText("Invalid category name: " + category);
                alert.showAndWait();
            }
        }
    }

    private void deleteProductFromDatabase(int productId) throws SQLException {
        connectToDatabase(url, username, password);
        if (connection != null) {
            String sql = "DELETE FROM products WHERE product_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();
            stmt.close();
            connection.close();

            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Deleted");
                alert.setHeaderText(null);
                alert.setContentText("Product deleted from the database.");
                alert.showAndWait();
            }
        }
    }

}
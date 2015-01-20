/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tosade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import tosade.domain.*;

/**
 *
 * @author Jelle
 */
public class ToolDatabase {
    private Connection connect = null;
    
    public ToolDatabase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }
        try {
            connect = DriverManager.getConnection("jdbc:oracle:thin:@yuno.jelleluteijn.nl:1521:xe", "kwartaal2b","kwartaal2b");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
    }
    
    public ArrayList<Task> fetchTasks() {
        ArrayList<Task> values = new ArrayList<Task>();
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM task where status = 'pending'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.id = resultSet.getInt("id");
                task.type_id = resultSet.getInt("type_id");
                task.schema_id = resultSet.getInt("schema_id");
                task.status = resultSet.getString("status");
                task.datetime = resultSet.getTimestamp("datetime");
                values.add(task);
            }
            resultSet.close();
            return values;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public void updateTask(Task task) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE task SET type_id = ?, schema_id = ?, status = ?, datetime = ? WHERE id = ?");
            preparedStatement.setInt(1, task.type_id);
            preparedStatement.setInt(2, task.schema_id);
            preparedStatement.setString(3, task.status);
            preparedStatement.setTimestamp(4, task.datetime);
            preparedStatement.setInt(5, task.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public ArrayList<TaskScript> fetchTaskScripts(int taskId) {
        ArrayList<TaskScript> values = new ArrayList<TaskScript>();
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM taskscript where task_id = ?");
            preparedStatement.setInt(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TaskScript taskScript = new TaskScript();
                taskScript.id = resultSet.getInt("id");
                taskScript.task_id = resultSet.getInt("task_id");
                taskScript.content = resultSet.getString("content");
                if(resultSet.getInt("is_done") == 0) {
                    taskScript.is_done = false;
                }else{
                    taskScript.is_done = true;
                }
                taskScript.feedback = resultSet.getString("feedback");
                values.add(taskScript);
            }
            resultSet.close();
            return values;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public int insertTaskScript(TaskScript taskScript) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("insert into taskscript (task_id, content, is_done, feedback) values (?, ?, ?, ?)");
            preparedStatement.setInt(1, taskScript.task_id);
            preparedStatement.setString(2, taskScript.content);
            if(taskScript.is_done == false) {
                preparedStatement.setInt(3, 0);
            }else{
                preparedStatement.setInt(3, 1);
            }
            preparedStatement.setString(4, taskScript.feedback);
            ResultSet resultSet = preparedStatement.executeQuery();

            preparedStatement = connect.prepareStatement("SELECT seq_taskscript_id.currval as idvalue FROM dual");
            resultSet = preparedStatement.executeQuery();

            int idvalue = 0;
            while (resultSet.next()) {
                idvalue = resultSet.getInt("idvalue");
            }
            resultSet.close();
            return idvalue;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return 0;
        }
    }
    
    public TargetSchema fetchSchema(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM targetschema where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            TargetSchema targetSchema = new TargetSchema();
            while (resultSet.next()) {
                targetSchema = new TargetSchema();
                targetSchema.id = resultSet.getInt("id");
                targetSchema.name = resultSet.getString("name");
                targetSchema.platform = resultSet.getString("platform");
                targetSchema.hostname = resultSet.getString("hostname");
                targetSchema.port = resultSet.getString("port");
                targetSchema.username = resultSet.getString("username");
                targetSchema.password = resultSet.getString("password");
                targetSchema.code = resultSet.getString("code");
                if(resultSet.getInt("overwriteschema") == 0) {
                    targetSchema.overwriteschema = false;
                }else{
                    targetSchema.overwriteschema = true;
                }
            }
            resultSet.close();
            return targetSchema;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public SchemaTable fetchSchemaTable(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM schematable where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            SchemaTable schemaTable = new SchemaTable();
            while (resultSet.next()) {
                schemaTable = new SchemaTable();
                schemaTable.id = resultSet.getInt("id");
                schemaTable.schema_id = resultSet.getInt("schema_id");
                schemaTable.name = resultSet.getString("name");
                schemaTable.code = resultSet.getString("code");
            }
            resultSet.close();
            return schemaTable;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<SchemaTable> fetchSchemaTables(int schemaId) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM schematable where schema_id = ?");
            preparedStatement.setInt(1, schemaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<SchemaTable> values = new ArrayList<SchemaTable>();
            while (resultSet.next()) {
                SchemaTable schemaTable = new SchemaTable();
                schemaTable.id = resultSet.getInt("id");
                schemaTable.schema_id = resultSet.getInt("schema_id");
                schemaTable.name = resultSet.getString("name");
                schemaTable.code = resultSet.getString("code");
                values.add(schemaTable);
            }
            resultSet.close();
            return values;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public void deleteSchemaTables(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM schematable where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public void updateSchemaTables(SchemaTable schemaTable) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE schematable SET schema_id = ?, name = ?, code = ? WHERE id = ?");
            preparedStatement.setInt(1, schemaTable.schema_id);
            preparedStatement.setString(2, schemaTable.name);
            preparedStatement.setString(3, schemaTable.code);
            preparedStatement.setInt(5, schemaTable.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public int insertSchemaTable(SchemaTable schemaTable) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO schematable (schema_id, name, code) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, schemaTable.schema_id);
            preparedStatement.setString(2, schemaTable.name);
            preparedStatement.setString(3, schemaTable.code);
            preparedStatement.executeUpdate();
            
            preparedStatement = connect.prepareStatement("SELECT seq_schematable_id.currval as idvalue FROM dual");
            ResultSet resultSet = preparedStatement.executeQuery();

            int idvalue = 0;
            while (resultSet.next()) {
                idvalue = resultSet.getInt("idvalue");
            }
            resultSet.close();
            return idvalue;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return 0;
        }
    }
    
    public SchemaTableField fetchSchemaTableField(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM schematablefield where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            SchemaTableField schemaTableField = new SchemaTableField();
            while (resultSet.next()) {
                schemaTableField = new SchemaTableField();
                schemaTableField.id = resultSet.getInt("id");
                schemaTableField.table_id = resultSet.getInt("table_id");
                schemaTableField.fields_id = resultSet.getInt("fields_id");
                schemaTableField.name = resultSet.getString("name");
                schemaTableField.type = resultSet.getString("type");
                schemaTableField.length = resultSet.getString("length");
                if(resultSet.getInt("primairykey") == 0) {
                    schemaTableField.primairykey = false;
                }else{
                    schemaTableField.primairykey = true;
                }
                if(resultSet.getInt("foreignkey") == 0) {
                    schemaTableField.foreignkey = false;
                }else{
                    schemaTableField.foreignkey = true;
                }
                schemaTableField.code = resultSet.getString("code");
                if(resultSet.getInt("autoincrement") == 0) {
                    schemaTableField.autoincrement = false;
                }else{
                    schemaTableField.autoincrement = true;
                }
            }
            resultSet.close();
            return schemaTableField;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<SchemaTableField> fetchSchemaTableFields(int schemaTableId) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM schematablefield where table_id = ?");
            preparedStatement.setInt(1, schemaTableId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<SchemaTableField> values = new ArrayList<SchemaTableField>();
            while (resultSet.next()) {
                SchemaTableField schemaTableField = new SchemaTableField();
                schemaTableField.id = resultSet.getInt("id");
                schemaTableField.table_id = resultSet.getInt("table_id");
                schemaTableField.fields_id = resultSet.getInt("fields_id");
                schemaTableField.name = resultSet.getString("name");
                schemaTableField.type = resultSet.getString("type");
                schemaTableField.length = resultSet.getString("length");
                if(resultSet.getInt("primairykey") == 0) {
                    schemaTableField.primairykey = false;
                }else{
                    schemaTableField.primairykey = true;
                }
                if(resultSet.getInt("foreignkey") == 0) {
                    schemaTableField.foreignkey = false;
                }else{
                    schemaTableField.foreignkey = true;
                }
                schemaTableField.code = resultSet.getString("code");
                if(resultSet.getInt("autoincrement") == 0) {
                    schemaTableField.autoincrement = false;
                }else{
                    schemaTableField.autoincrement = true;
                }
                values.add(schemaTableField);
            }
            resultSet.close();
            return values;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public void deleteSchemaTableField(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM schematablefield where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public void updateSchemaTableField(SchemaTableField schemaTableField) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE schematablefield SET table_id = ?, fields_id = ?, name = ?, type = ?, length = ?, primairykey = ?, foreignkey = ?, code = ?, autoincrement = ? WHERE id = ?");
            preparedStatement.setInt(1, schemaTableField.table_id);
            preparedStatement.setInt(2, schemaTableField.fields_id);
            preparedStatement.setString(3, schemaTableField.name);
            preparedStatement.setString(4, schemaTableField.type);
            preparedStatement.setString(5, schemaTableField.length);
            if(schemaTableField.primairykey == false) {
                preparedStatement.setInt(6, 0);
            }else{
                preparedStatement.setInt(6, 1);
            }
            if(schemaTableField.foreignkey == false) {
                preparedStatement.setInt(7, 0);
            }else{
                preparedStatement.setInt(7, 1);
            }
            preparedStatement.setString(8, schemaTableField.code);
            if(schemaTableField.autoincrement == false) {
                preparedStatement.setInt(9, 0);
            }else{
                preparedStatement.setInt(0, 1);
            }
            preparedStatement.setInt(10, schemaTableField.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public int insertSchemaTableField(SchemaTableField schemaTableField) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO schematablefield (table_id, fields_id, name, type, length, primairykey, foreignkey, code, autoincrement) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, schemaTableField.table_id);
            preparedStatement.setInt(2, schemaTableField.fields_id);
            preparedStatement.setString(3, schemaTableField.name);
            preparedStatement.setString(4, schemaTableField.type);
            preparedStatement.setString(5, schemaTableField.length);
            if(schemaTableField.primairykey == false) {
                preparedStatement.setInt(6, 0);
            }else{
                preparedStatement.setInt(6, 1);
            }
            if(schemaTableField.foreignkey == false) {
                preparedStatement.setInt(7, 0);
            }else{
                preparedStatement.setInt(7, 1);
            }
            preparedStatement.setString(8, schemaTableField.code);
            if(schemaTableField.autoincrement == false) {
                preparedStatement.setInt(9, 0);
            }else{
                preparedStatement.setInt(9, 1);
            }
            preparedStatement.executeUpdate();
            
            preparedStatement = connect.prepareStatement("SELECT seq_schematablefield_id.currval as idvalue FROM dual");
            ResultSet resultSet = preparedStatement.executeQuery();

            int idvalue = 0;
            while (resultSet.next()) {
                idvalue = resultSet.getInt("idvalue");
            }
            resultSet.close();
            return idvalue;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return 0;
        }
    }
    
    public Category fetchCategory(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM category where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Category category = new Category();
            while (resultSet.next()) {
                category = new Category();
                category.id = resultSet.getInt("id");
                category.name = resultSet.getString("name");
            }
            resultSet.close();
            return category;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<Category> fetchCategories() {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM category");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Category> values = new ArrayList<Category>();
            while (resultSet.next()) {
                Category category = new Category();
                category.id = resultSet.getInt("id");
                category.name = resultSet.getString("name");
                values.add(category);
            }
            resultSet.close();
            return values;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public void deleteCategory(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM category where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public void updateCategory(Category category) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE category SET name = ? WHERE id = ?");
            preparedStatement.setString(1, category.name);
            preparedStatement.setInt(2, category.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public int insertCategory(Category category) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO category (name) VALUES (?)");
            preparedStatement.setString(1, category.name);
            preparedStatement.executeUpdate();
            
            preparedStatement = connect.prepareStatement("SELECT seq_category_id.currval as idvalue FROM dual");
            ResultSet resultSet = preparedStatement.executeQuery();

            int idvalue = 0;
            while (resultSet.next()) {
                idvalue = resultSet.getInt("idvalue");
            }
            resultSet.close();
            return idvalue;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return 0;
        }
    }
    
    public BusinessRuleType fetchBusinessRuleType(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM businessruletype where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            BusinessRuleType businessRuleType = new BusinessRuleType();
            while (resultSet.next()) {
                businessRuleType = new BusinessRuleType();
                businessRuleType.id = resultSet.getInt("id");
                businessRuleType.category_id = resultSet.getInt("category_id");
                businessRuleType.name = resultSet.getString("name");
                businessRuleType.code = resultSet.getString("code");
                businessRuleType.description = resultSet.getString("description");
                businessRuleType.example = resultSet.getString("example");
            }
            resultSet.close();
            return businessRuleType;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<BusinessRuleType> fetchBusinessRuleTypes(int categoryId) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM businessruletype where category = ?");
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<BusinessRuleType> values = new ArrayList<BusinessRuleType>();
            while (resultSet.next()) {
                BusinessRuleType businessRuleType = new BusinessRuleType();
                businessRuleType.id = resultSet.getInt("id");
                businessRuleType.category_id = resultSet.getInt("category_id");
                businessRuleType.name = resultSet.getString("name");
                businessRuleType.code = resultSet.getString("code");
                businessRuleType.description = resultSet.getString("description");
                businessRuleType.example = resultSet.getString("example");
                values.add(businessRuleType);
            }
            resultSet.close();
            return values;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public void deleteBusinessRuleType(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM category where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public void updateBusinessRuleType(BusinessRuleType businessRuleType) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE businessruletype SET category_id = ?, name = ?, code = ?, description = ?, example = ? WHERE id = ?");
            preparedStatement.setInt(1, businessRuleType.category_id);
            preparedStatement.setString(2, businessRuleType.name);
            preparedStatement.setString(3, businessRuleType.code);
            preparedStatement.setString(4, businessRuleType.description);
            preparedStatement.setString(5, businessRuleType.example);
            preparedStatement.setInt(6, businessRuleType.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public int insertBusinessRuleType(BusinessRuleType businessRuleType) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO businessruletype (category_id, name, code, description, example) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, businessRuleType.category_id);
            preparedStatement.setString(2, businessRuleType.name);
            preparedStatement.setString(3, businessRuleType.code);
            preparedStatement.setString(4, businessRuleType.description);
            preparedStatement.setString(5, businessRuleType.example);
            preparedStatement.executeUpdate();
            
            preparedStatement = connect.prepareStatement("SELECT seq_businessruletype_id.currval as idvalue FROM dual");
            ResultSet resultSet = preparedStatement.executeQuery();

            int idvalue = 0;
            while (resultSet.next()) {
                idvalue = resultSet.getInt("idvalue");
            }
            resultSet.close();
            return idvalue;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return 0;
        }
    }
    
    public Operator fetchOperator(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM operator where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Operator operator = new Operator();
            while (resultSet.next()) {
                operator = new Operator();
                operator.id = resultSet.getInt("id");
                operator.type_id = resultSet.getInt("type_id");
                operator.name = resultSet.getString("name");
                operator.type = resultSet.getString("type");
            }
            resultSet.close();
            return operator;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<Operator> fetchOperators(int businessRuleTypeId) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM operator where type_id = ?");
            preparedStatement.setInt(1, businessRuleTypeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Operator> values = new ArrayList<Operator>();
            while (resultSet.next()) {
                Operator operator = new Operator();
                operator.id = resultSet.getInt("id");
                operator.type_id = resultSet.getInt("type_id");
                operator.name = resultSet.getString("name");
                operator.type = resultSet.getString("type");
                values.add(operator);
            }
            resultSet.close();
            return values;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public void deleteOperator(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM operator where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public void updateOperator(Operator operator) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE operator SET type_id = ?, name = ?, type = ? WHERE id = ?");
            preparedStatement.setInt(1, operator.type_id);
            preparedStatement.setString(2, operator.name);
            preparedStatement.setString(3, operator.type);
            preparedStatement.setInt(4, operator.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public int insertOperator(Operator operator) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO operator (type_id, name, type) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, operator.type_id);
            preparedStatement.setString(2, operator.name);
            preparedStatement.setString(3, operator.type);
            preparedStatement.executeUpdate();
            
            preparedStatement = connect.prepareStatement("SELECT seq_operator_id.currval as idvalue FROM dual");
            ResultSet resultSet = preparedStatement.executeQuery();

            int idvalue = 0;
            while (resultSet.next()) {
                idvalue = resultSet.getInt("idvalue");
            }
            resultSet.close();
            return idvalue;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return 0;
        }
    }
    
    public ErrorType fetchErrorType(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM operator where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            ErrorType errorType = new ErrorType();
            while (resultSet.next()) {
                errorType = new ErrorType();
                errorType.id = resultSet.getInt("id");
                errorType.name = resultSet.getString("name");
                errorType.type = resultSet.getString("type");
            }
            resultSet.close();
            return errorType;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<ErrorType> fetchErrorTypes() {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM errortype");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<ErrorType> values = new ArrayList<ErrorType>();
            while (resultSet.next()) {
                ErrorType errorType = new ErrorType();
                errorType.id = resultSet.getInt("id");
                errorType.name = resultSet.getString("name");
                errorType.type = resultSet.getString("type");
                values.add(errorType);
            }
            resultSet.close();
            return values;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public void deleteErrorType(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM errortype where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public void updateErrorType(ErrorType errorType) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE errortype SET name = ?, type = ? WHERE id = ?");
            preparedStatement.setString(1, errorType.name);
            preparedStatement.setString(2, errorType.type);
            preparedStatement.setInt(3, errorType.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public int insertErrorType(ErrorType errorType) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO errortype (name, type) VALUES (?, ?)");
            preparedStatement.setString(1, errorType.name);
            preparedStatement.setString(2, errorType.type);
            preparedStatement.executeUpdate();
            
            preparedStatement = connect.prepareStatement("SELECT seq_errortype_id.currval as idvalue FROM dual");
            ResultSet resultSet = preparedStatement.executeQuery();

            int idvalue = 0;
            while (resultSet.next()) {
                idvalue = resultSet.getInt("idvalue");
            }
            resultSet.close();
            return idvalue;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return 0;
        }
    }
    
    public BusinessRule fetchBusinessRule(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM businessrule where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            BusinessRule businessRule = new BusinessRule();
            while (resultSet.next()) {
                businessRule = new BusinessRule();
                businessRule.id = resultSet.getInt("id");
                businessRule.type_id = resultSet.getInt("type_id");
                businessRule.field_id = resultSet.getInt("field_id");
                businessRule.error_id = resultSet.getInt("error_id");
                businessRule.trigger_event = resultSet.getString("trigger_event");
                businessRule.error_message = resultSet.getString("error_message");
            }
            resultSet.close();
            return businessRule;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<BusinessRule> fetchBusinessRules(int fieldId) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM businessrule where field_id = ?");
            preparedStatement.setInt(1, fieldId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<BusinessRule> values = new ArrayList<BusinessRule>();
            while (resultSet.next()) {
                BusinessRule businessRule = new BusinessRule();
                businessRule.id = resultSet.getInt("id");
                businessRule.type_id = resultSet.getInt("type_id");
                businessRule.field_id = resultSet.getInt("field_id");
                businessRule.error_id = resultSet.getInt("error_id");
                businessRule.trigger_event = resultSet.getString("trigger_event");
                businessRule.error_message = resultSet.getString("error_message");
                values.add(businessRule);
            }
            resultSet.close();
            return values;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public void deleteBusinessRule(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM businessrule where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public void updateBusinessRule(BusinessRule businessRule) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE operator SET type_id = ?, field_id = ?, error_id = ?, trigger_event = ?, error_message = ? WHERE id = ?");
            preparedStatement.setInt(1, businessRule.type_id);
            preparedStatement.setInt(2, businessRule.field_id);
            preparedStatement.setInt(3, businessRule.error_id);
            preparedStatement.setString(4, businessRule.trigger_event);
            preparedStatement.setString(5, businessRule.error_message);
            preparedStatement.setInt(6, businessRule.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public int insertBusinessRule(BusinessRule businessRule) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO operator (type_id, field_id, error_id, trigger_event, error_message) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, businessRule.type_id);
            preparedStatement.setInt(2, businessRule.field_id);
            preparedStatement.setInt(3, businessRule.error_id);
            preparedStatement.setString(4, businessRule.trigger_event);
            preparedStatement.setString(5, businessRule.error_message);
            preparedStatement.executeUpdate();
            
            preparedStatement = connect.prepareStatement("SELECT seq_businessrule_id.currval as idvalue FROM dual");
            ResultSet resultSet = preparedStatement.executeQuery();

            int idvalue = 0;
            while (resultSet.next()) {
                idvalue = resultSet.getInt("idvalue");
            }
            resultSet.close();
            return idvalue;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return 0;
        }
    }
    
    public OperatorValue fetchOperatorValue(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM operatorvalue where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            OperatorValue operatorValue = new OperatorValue();
            while (resultSet.next()) {
                operatorValue = new OperatorValue();
                operatorValue.id = resultSet.getInt("id");
                operatorValue.rule_id = resultSet.getInt("rule_id");
                operatorValue.type_id = resultSet.getInt("type_id");
                operatorValue.value = resultSet.getString("value");
            }
            resultSet.close();
            return operatorValue;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<OperatorValue> fetchOperatorValues(int BusinessRuleId, int OperatorId) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM operatorvalue where rule_id = ? and type_id = ?");
            preparedStatement.setInt(1, BusinessRuleId);
            preparedStatement.setInt(1, OperatorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<OperatorValue> values = new ArrayList<OperatorValue>();
            while (resultSet.next()) {
                OperatorValue operatorValue = new OperatorValue();
                operatorValue.id = resultSet.getInt("id");
                operatorValue.rule_id = resultSet.getInt("rule_id");
                operatorValue.type_id = resultSet.getInt("type_id");
                operatorValue.value = resultSet.getString("value");
                values.add(operatorValue);
            }
            resultSet.close();
            return values;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return null;
        }
    }
    
    public void deleteOperatorValue(int id) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM operatorvalue where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public void updateOperatorValue(OperatorValue operatorValue) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("UPDATE operatorvalue SET rule_id = ?, type_id = ?, value = ? WHERE id = ?");
            preparedStatement.setInt(1, operatorValue.rule_id);
            preparedStatement.setInt(2, operatorValue.type_id);
            preparedStatement.setString(3, operatorValue.value);
            preparedStatement.setInt(4, operatorValue.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
        }
    }
    
    public int insertOperatorValue(OperatorValue operatorValue) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO operator (rule_id, type_id, value) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, operatorValue.rule_id);
            preparedStatement.setInt(2, operatorValue.type_id);
            preparedStatement.setString(3, operatorValue.value);
            preparedStatement.executeUpdate();
            
            preparedStatement = connect.prepareStatement("SELECT seq_operatorvalue_id.currval as idvalue FROM dual");
            ResultSet resultSet = preparedStatement.executeQuery();

            int idvalue = 0;
            while (resultSet.next()) {
                idvalue = resultSet.getInt("idvalue");
            }
            resultSet.close();
            return idvalue;
        } catch (SQLException e) {
            System.out.println("Query Failed! Check output console");
            e.printStackTrace();
            return 0;
        }
    }
}
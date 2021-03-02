/*
* @author: NIYONZIMA Stecie
* 
   * @Description
   * This class has a query for inserting data into database
   * done on 9 Feb 2021
*/
package com.customify.server.services;

import com.customify.server.Db.Db;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jdk.jshell.spi.ExecutionControl.ExecutionControlException;

// import com.mysql.cj.protocol.Resultset;
// import com.mysql.cj.protocol.x.ResultMessageListener;
// import com.mysql.cj.xdevapi.Statement;
// import com.mysql.cj.protocol.Resultset;
import com.customify.server.data_format.CustomerFeedback.CustomerFeedbackDataFormat;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomerFeedbackService {
    Socket socket;
    OutputStream output;
    ObjectOutputStream objectOutput;

    public CustomerFeedbackService(Socket socket) throws IOException {
        this.socket = socket;
        this.output = socket.getOutputStream();
        this.objectOutput = new ObjectOutputStream(output);
    }

    // function to send data to the database
    public void Feedback(String data) throws SQLException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);
        Connection connection = Db.getConnection();
        String feedbackQuery = "INSERT INTO customerFeedback values (?,?,?,?,NOW())";

        Statement stmt = Db.getStatement();
        ResultSet checkBusiness = stmt.executeQuery("SELECT * FROM businesses");

        try {

            // prepare the statement to be sent to the database
            while (checkBusiness.next()) {
                if (jsonNode.get("businessId").asInt() == checkBusiness.getInt("id")) {
                    PreparedStatement statement = connection.prepareStatement(feedbackQuery);
                    statement.setInt(1, jsonNode.get("customerId").asInt());
                    statement.setInt(2, jsonNode.get("businessId").asInt());
                    statement.setString(3, jsonNode.get("title").asText());
                    statement.setString(4, jsonNode.get("description").asText());

                    if (statement.execute()) {
                        System.out.println("Something went wrong");
                    } else {
                        System.out.println("Query Ok !!! ");
                        break;
                    }
                } else {
                    System.out
                            .println("The business whith id " + jsonNode.get("businessId").asInt() + " does not exist");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void getAllFeedbacks() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Statement stmt = Db.getStatement();
        String query = "SELECT * FROM customerFeedback";
        List<String> feedbacks = new ArrayList<>();

        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                CustomerFeedbackDataFormat cf = new CustomerFeedbackDataFormat(
                        rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDate(8).toString());
                String json = objectMapper.writeValueAsString(cf);
                feedbacks.add(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package zootest.zooinventory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Operations {
	/**
	 * Adds new Feed Quantity to the Inventory
	 * 
	 * zooId --  the zoo in which new quantity arrived
	 *          
	 * newQuantity -- the weight of new quantity
	 *            
	 */
	public static void addNewQuantity(int zooId, double newQuantity) {
		String updateQuery = "UPDATE zoo SET new_inventory = new_inventory+" + newQuantity + " WHERE zooid = " + zooId;
		performUpdate(updateQuery);
		System.out.println("New shipment has been updated!");
	}

	/**
	 * Method records feed time and quantity for each animal in a given zoo
	 * 
	 * zooId -- the zoo in which feed time and quantity needs to be updated
	 *            
	 * in --  scanner object to read inputs
	 *           
	 */
	public static void recordFeedTime(int zooId, Scanner in) {
		String selectAnimalsQuery = "SELECT * FROM animal WHERE zooid = " + zooId;
		Connection con = (Connection) DbConnection.getInstance();
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(selectAnimalsQuery);
			String animalId, species;
			ArrayList<String> updateQueries = new ArrayList<String>();
			while (rs.next()) {
				animalId = rs.getString("animalid");
				species = rs.getString("species");
				System.out.println("Enter feed time for " + species + "(id:" + animalId + "): ");
				int feedTime = in.nextInt();
				System.out.println("Enter feed quantity for " + species + "(id:" + animalId + "): ");
				double feedQty = in.nextDouble();
				String updateQuery = "UPDATE animal SET feedtime = " + feedTime + ", quantity = " + feedQty + " WHERE animalId = " + animalId;
				updateQueries.add(updateQuery); // Create set of update queries which will be executed after this loop
			}
			for (String updateQuery : updateQueries) {
				PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(updateQuery);
				preparedStmt.execute();
			}
			System.out.println("Feed times recorded");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method empties new inventory and adds it to running inventory
	 * 
	 * zooId -- zoo in which inventory replacement needs to happen
	 *            
	 */
	public static void replaceInventory(int zooId) {
		String updateQuery = "UPDATE zoo SET running_inventory = running_inventory + new_inventory, new_inventory = 0 WHERE zooid = " + zooId;
		performUpdate(updateQuery);
		System.out.println("Inventory Updated");
	}

	/**
	 * Method gets a query and executes it
	 * 
	 * updateQuery --  the query to be executed
	 *           
	 */
	private static void performUpdate(String updateQuery) {
		Connection con = (Connection) DbConnection.getInstance();
		try {
			PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(updateQuery);
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

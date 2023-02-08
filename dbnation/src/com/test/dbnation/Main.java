package com.test.dbnation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/db_nation";
		String user = "root";
		String password = "4Ghlpe@!1234de";
		Scanner scan = new Scanner(System.in);
		System.out.print("Inserire la ricerca");
		String ricercationName = scan.nextLine();
		scan.close();
		try (Connection con = DriverManager.getConnection(url, user, password)) {

			String sql = "SELECT countries.country_id  as id_paese ,countries.name as nome_paese, "
					+ "regions.name as nome_regione, " + "continents.name as nome_continente\n" + "FROM countries \n"
					+ "Inner join regions \n" + "on countries.region_id  = regions.region_id  \n"
					+ "Inner join continents \n" + "on regions.continent_id = continents.continent_id \n"
					+"WHERE countries.name LIKE ?"
					+ "Order by countries.name ";

			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, ricercationName);

				try (ResultSet rs = ps.executeQuery()) {

					while (rs.next()) {
						int id = rs.getInt(1);
						String name = rs.getString(2);
						String regionName = rs.getString(3);
						String continentName = rs.getString(4);
						System.out.println(id + " - " + name + " - " + regionName + " - " + continentName);
					}
				}
			}
		} catch (SQLException ex) {

			ex.printStackTrace();
		}
	}
}

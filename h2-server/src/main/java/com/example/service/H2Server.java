package com.example.service;


import java.sql.SQLException;
import org.h2.tools.Console;

public class H2Server {
  public static void main(String[] args) throws SQLException {
    Console.main( "-webPort", "6565", "-tcpPort", "9096");
  }
}

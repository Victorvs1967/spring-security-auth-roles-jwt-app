package com.vvs.springsecurityauthrolesjwtapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

  String zip;
  String country;
  String city;
  String street;
  String building;
  String appartament;
}

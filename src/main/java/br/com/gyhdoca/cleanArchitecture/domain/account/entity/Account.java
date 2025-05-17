package br.com.gyhdoca.cleanArchitecture.domain.account.entity;

import br.com.gyhdoca.cleanArchitecture.domain.countries.entity.Countries;

import java.util.List;

public class Account {

    private Long id;
    private String name;
    private String email;
    private List<Countries> countries;
}

package com.example.CalculadoraObraResidencial.Entities;

import jakarta.persistence.*;


import java.util.List;

@Entity

public class Projeto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nomeProjeto;

        @OneToMany(cascade = CascadeType.ALL)
        private List<Comodo> comodos;

        @OneToMany(cascade = CascadeType.ALL)
        private List<Pilar> pilares;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getNomeProjeto() {
                return nomeProjeto;
        }

        public void setNomeProjeto(String nomeProjeto) {
                this.nomeProjeto = nomeProjeto;
        }

        public List<Comodo> getComodos() {
                return comodos;
        }

        public void setComodos(List<Comodo> comodos) {
                this.comodos = comodos;
        }

        public List<Pilar> getPilares() {
                return pilares;
        }

        public void setPilares(List<Pilar> pilares) {
                this.pilares = pilares;
        }

        public Projeto(Long id, String nomeProjeto, List<Comodo> comodos, List<Pilar> pilares) {
                this.id = id;
                this.nomeProjeto = nomeProjeto;
                this.comodos = comodos;
                this.pilares = pilares;
        }

        public Projeto() {
        }
}

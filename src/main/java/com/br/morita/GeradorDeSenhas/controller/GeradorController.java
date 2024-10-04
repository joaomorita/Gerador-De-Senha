package com.br.morita.GeradorDeSenhas.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin("*") // Liberar o acesso a outro dominio
@RestController
public class GeradorController {

    // Selecionando as strings que vou usar
    private static final String LETRAS_MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
    private static final String LETRAS_MAIUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMEROS = "0123456789";
    private static final String SIMBOLOS = "!@#$%^&*()-_=+[]{};:,.<>?";

    private static final SecureRandom random = new SecureRandom(); // Gera os números de forma aleatória

    private String gerarSenha(int comprimento){
        StringBuilder conjuntoCaracteres = new StringBuilder(); // Para modificar os caracteres e gerar novo objeto
        List<Character> senha = new ArrayList<>();

        // Adicionar os caracteres ao conjunto
        conjuntoCaracteres.append(LETRAS_MINUSCULAS);
        conjuntoCaracteres.append(LETRAS_MAIUSCULAS);
        conjuntoCaracteres.append(NUMEROS);
        conjuntoCaracteres.append(SIMBOLOS);

        // Garantir um caractere de cada tipo
        senha.add(LETRAS_MINUSCULAS.charAt(random.nextInt(LETRAS_MINUSCULAS.length())));
        senha.add(LETRAS_MAIUSCULAS.charAt(random.nextInt(LETRAS_MAIUSCULAS.length())));
        senha.add(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
        senha.add(SIMBOLOS.charAt(random.nextInt(SIMBOLOS.length())));

        // Preencher o restante com caracteres aleatórios
        for(int i = senha.size(); i < comprimento; i++){
            int index = random.nextInt(conjuntoCaracteres.length());
            senha.add(conjuntoCaracteres.charAt(index));
        }

        Collections.shuffle(senha, random); // Usando o collections para misturar os caracteres da senha

        // Transformar em string
        StringBuilder senhaFinal = new StringBuilder();
        for (char c : senha) {
            senhaFinal.append(c);
        }

        return senhaFinal.toString();
    }

    public static class SenhaResponse{
        private String senha;

        public SenhaResponse(String senha){
            this.senha = senha;
        }

        public String getSenha(){
            return senha;
        }

        public void setSenha(String senha){
            this.senha = senha;
        }
    }

    @GetMapping("/gerar-senha") // O get mapping para o front acessar
    public SenhaResponse novaSenha(
            @RequestParam(defaultValue = "14" /* Define o tamanho da senha */) int comprimento) {
        String senha = gerarSenha(comprimento);
        return new SenhaResponse(senha);
    }
}
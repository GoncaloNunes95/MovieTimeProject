package com.example.movietime.data.mapper;

import com.example.movietime.data.Filme;
import com.example.movietime.network.response.FilmeResponse;

import java.util.ArrayList;
import java.util.List;

public class FilmeMapper {

    public static List<Filme> ResponseToDominio(List<FilmeResponse> listaFilmeResponse) {

        List<Filme> filmeList = new ArrayList<>();

        for (FilmeResponse filmeResponse : listaFilmeResponse) {

            final Filme filme = new Filme(filmeResponse.getId(), filmeResponse.getPoster_path(), filmeResponse.getOriginal_title(), filmeResponse.getRelease_date(),
                    filmeResponse.getVoteAverage(), filmeResponse.getSinopse());
            filmeList.add(filme);

        }
        return filmeList;
    }
}

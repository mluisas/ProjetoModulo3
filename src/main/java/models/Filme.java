package models;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.Year;
import java.util.List;
import java.util.Objects;

public class Filme {
    private final Integer rank;
    private final String title;
    private final List<String> genre;
    private final String description;
    private final String director;
    private final List<String> actors;
    private final Year year;
    private final LocalTime runtime;
    private final Double rating;
    private final Integer votes;
    private final BigDecimal revenue;

    public Filme(Integer rank, String title, List<String> genre, String description, String director, List<String> actors, Year year, LocalTime runtime, Double rating, Integer votes, BigDecimal revenue) {
        this.rank = rank;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.director = director;
        this.actors = actors;
        this.year = year;
        this.runtime = runtime;
        this.rating = rating;
        this.votes = votes;
        this.revenue = revenue;
    }

    public Integer getRank() {
        return rank;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getActors() {
        return actors;
    }

    public Year getYear() {
        return year;
    }

    public LocalTime getRuntime() {
        return runtime;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filme filme = (Filme) o;
        return title.equals(filme.title) && genre.equals(filme.genre) && description.equals(filme.description) && director.equals(filme.director) && actors.equals(filme.actors) && year.equals(filme.year) && runtime.equals(filme.runtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, description, director, actors, year, runtime);
    }

    @Override
    public String toString() {
        return "Filme{" +
                "rank=" + rank +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                ", description='" + description + '\'' +
                ", director='" + director + '\'' +
                ", actors=" + actors +
                ", year=" + year +
                ", runtime=" + runtime +
                ", rating=" + rating +
                ", votes=" + votes +
                ", revenue=" + revenue +
                '}';
    }
}

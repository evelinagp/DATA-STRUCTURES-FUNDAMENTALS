import java.util.*;
import java.util.stream.Collectors;

public class OlympicsImpl implements Olympics {

    private Map<Integer, Competition> competitionById;
    private Map<Integer, Competitor> competitorById;


    public OlympicsImpl() {
        this.competitionById = new HashMap<>();
        this.competitorById = new HashMap<>();
    }

    @Override
    public void addCompetitor(int id, String name) {
        Competitor competitor = new Competitor(id, name);
        if (exist(competitor)) {
            throw new IllegalArgumentException();
        }
        this.competitorById.put(competitor.getId(), competitor);
    }

    private boolean exist(Competitor competitor) {
        return this.competitorById.containsKey(competitor.getId());
    }


    @Override
    public void addCompetition(int id, String name, int score) {
        Competition competition = new Competition(name, id, score);
        if (exist(competition)) {
            throw new IllegalArgumentException();
        }

        this.competitionById.put(id, competition);

    }

    private boolean exist(Competition competition) {
        return this.competitionById.containsKey(competition.getId());
    }


    @Override
    public void compete(int competitorId, int competitionId) {
        Competitor competitor = this.competitorById.get(competitorId);
        Competition competition = this.competitionById.get(competitionId);
        if (competition == null || competitor == null) {
            throw new IllegalArgumentException();
        }
        competition.getCompetitors().add(competitor);
        competitor.setTotalScore(competitor.getTotalScore() + competition.getScore());
    }


    @Override
    public void disqualify(int competitionId, int competitorId) {
        Competition competition = this.competitionById.get(competitionId);
        if (competition == null) {
            throw new IllegalArgumentException();
        }
        Competitor competitorWithoutCompetition = this.competitorById.get(competitorId);
        boolean contains = this.competitionById.get(competitionId).getCompetitors().contains(competitorWithoutCompetition);
        if (!contains) {
            throw new IllegalArgumentException();

        }
        competition.getCompetitors().remove(competitorWithoutCompetition);
        competitorWithoutCompetition.setTotalScore(competitorWithoutCompetition.getTotalScore() - competition.getScore());
    }

    @Override

    public Iterable<Competitor> findCompetitorsInRange(long min, long max) {
        return this.competitorById.values().stream()
                .filter(competitor -> competitor.getTotalScore() > min && competitor.getTotalScore() <= max)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Competitor> getByName(String name) {
        List<Competitor> competitorList = this.competitorById.values().stream().filter(competitor ->
                competitor.getName().equals(name)).sorted(Comparator.comparingInt(Competitor::getId)).collect(Collectors.toList());
        if (competitorList.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return competitorList;
    }

    @Override
    public Iterable<Competitor> searchWithNameLength(int minLength, int maxLength) {
        return this.competitorById.values().stream().filter(competitor ->
                competitor.getName().length() >= minLength && competitor.getName().length() <= maxLength)
                .sorted(Comparator.comparingInt(Competitor::getId)).collect(Collectors.toList());
    }

    @Override
    public Boolean contains(int competitionId, Competitor comp) {
        Competition competition = this.competitionById.get(competitionId);

        if (competition == null) {
            throw new IllegalArgumentException();
        }
        return competition.getCompetitors().contains(comp);
    }

    @Override
    public int competitionsCount() {
        return this.competitionById.size();

    }

    @Override
    public int competitorsCount() {
        return this.competitorById.size();
    }

    @Override
    public Competition getCompetition(int id) {
        Competition competition = this.competitionById.values().stream().filter(comp -> comp.getId() == id).findFirst()
                .orElse(null);
        if (competition == null) {
            throw new IllegalArgumentException();
        }
        return competition;
    }
}

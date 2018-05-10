package game;

import errors.ParsingError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Scores {
    public static HashMap<String, Integer> parseScores(String scores) throws ParsingError {
        if (! scores.matches("(\\w+\\*\\d+)(\\*(\\w+\\*\\d+))?")) {
            throw new ParsingError(scores + " is not a valid scores String !");
        }

        HashMap<String, Integer> result = new HashMap<>();
        String[] components = scores.split("\\*");

        for (int i = 0; i <= components.length / 2; i += 2) {
            String userName = components[i];
            Integer score = Integer.valueOf(components[i + 1]);
            result.put(userName, score);
        }

        return result;
    }

    public static HashMap<String, ArrayList<String>> parsePlayedWords(Set<String> players,
                                                                      String words) throws ParsingError {
        String[] components = words.split("\\*");
        if (! players.contains(components[0])) {
            throw new ParsingError(words + " should start with a username !");
        }

        HashMap<String, ArrayList<String>> playedWords = new HashMap<>();
        String currentUser = "";
        for (String s: components) {
            if (players.contains(s) && ! playedWords.containsKey(s)) {
                currentUser = s;
                playedWords.put(currentUser, new ArrayList<>());
            } else {
                playedWords.get(currentUser).add(s);
            }
        }

        return playedWords;
    }
}

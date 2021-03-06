package bergeron.bot;

import com.sun.javafx.collections.SetListenerHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class BergeronBot {
    static Document page;
    static List<String> ListURL = new ArrayList<String>();
    static List<String> UrlExplorer = new ArrayList<>();
    static List<String> Courriel = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int nombrepageexplo = 0;
        int nombrecourrielext = 0;

        System.out.println("Bonjour Philippe" + "\n" + "\n" + "Tout va bien, explorons");
        ListURL.add(args[1]);
        Elements url1 = null;
        for (int i = 0; i <= Integer.parseInt(args[0]); i++) {
            for (String urltrouve : ListURL) {
                if (UrlExplorer.contains(urltrouve))
                    continue;
                UrlExplorer.add(urltrouve);

                page = Jsoup.connect(urltrouve).get();
                url1 = page.select("a[href]");
                Jsoup.connect(page.location()).get();
                EXplorer(urltrouve);
            }
            for (String Explore : ListURL) {
                if (UrlExplorer.contains(Explore))
                    continue;
                UrlExplorer.add(Explore);
            }
            ListURL.clear();
            for (Element lien : url1) {
                ListURL.add(lien.attr("abs:href"));
            }

        }
        for (String liens : UrlExplorer) {
            System.out.println("Exploration de >> " + liens);
        }

        System.out.println("\n" + "Nombre de page explorées : " + UrlExplorer.size() + "\n" + "Nombre de courriels extraits " +
                "(en ordre alphabétique) : " + Courriel.size());

        Courriel.sort(Comparator.<String>naturalOrder());
        for (String email : Courriel) {
            System.out.println("\t" + email);
        }
    }

    public static void EXplorer(String url) throws IOException {
        Pattern EmailPattern = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",
                Pattern.CASE_INSENSITIVE);
        Matcher EmailMatch = EmailPattern.matcher(page.text());
        while (EmailMatch.find()) {
            if (!Courriel.contains(EmailMatch.group()))
                Courriel.add(EmailMatch.group());
        }
    }
}



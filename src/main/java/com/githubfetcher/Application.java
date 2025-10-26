 package com.githubfetcher;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.githubfetcher")
public class Application {

public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
    
    GithubFetcherService fetcherService = context.getBean(GithubFetcherService.class);
    
    if (args.length == 2) {
        try {
            String repoUrl = args[0];
            int issueNumber = Integer.parseInt(args[1]);
            
            String issueData = fetcherService.apply(repoUrl, issueNumber);
            System.out.println("Issue data: " + issueData);
            
        } catch (NumberFormatException e) {
            System.err.println("Error: Issue number must be an integer");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error fetching issue: " + e.getMessage());
            System.exit(1);
        }
    } else {
        System.out.println("Usage: java -jar github-fetcher-1.0.0.jar <repository-url> <issue-number>");
        System.out.println("Example: java -jar github-fetcher-1.0.0.jar https://github.com/spring-projects/spring-framework 1");
        System.exit(1);
    }
}

}

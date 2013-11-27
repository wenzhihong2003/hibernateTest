package model;

import cfg.SessionTemplate;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.Date;

/**
 * User: wenzhihong
 * Date: 13-11-27
 * Time: 上午9:16
 */
public class NewsTest extends BaseTest {
    @Test
    public void testNewsOne2One() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                News news = new News();
                news.setTitle("第一条新闻");
                news.setPubDate(new Date());
                news.setSubTitle("子标题");
                news.setSubContent("子内容");
                news.setContent("内容...............");
                NewsDetail newsDetail = new NewsDetail();
                newsDetail.setContent("主....");
                newsDetail.setSubContent("主.....主");
                news.setNewsDetail(newsDetail);
                newsDetail.setNews(news);
                session.save(news);
            }
        }.exec(sessionFactory);

    }

        @Test
        public void testNews() throws Exception {
            new SessionTemplate() {
                @Override
                protected void doInSession() throws MalformedURLException {
                    News news = new News();
                    news.setTitle("第一条新闻");
                    news.setPubDate(new Date());
                    news.setSubTitle("子标题");
                    news.setSubContent("子内容");
                    news.setContent("内容...............");

                    session.save(news);

                    System.out.println(news.getId());

                }
            }.exec(sessionFactory);

            new SessionTemplate() {
                @Override
                protected void doInSession() throws MalformedURLException {
                    News n = (News) session.get(News.class, 1L);
                    System.out.println(n.getTitle());
                    //System.out.println(n.getContent());
                }
            }.exec(sessionFactory);

        }

}

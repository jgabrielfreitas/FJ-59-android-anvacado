package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.converter.BlogPostConverter;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.webservice.Pagina;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by erich on 7/16/13.
 */
public class BuscaMaisPostsTask extends AsyncTask<Pagina, Void, List<BlogPost>> {

    private Exception erro;
    private MainActivity activity;

    public BuscaMaisPostsTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<BlogPost> doInBackground(Pagina... paginas) {
        try {
            Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();

            String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar).get();

            List<BlogPost> postsRecebidos = new BlogPostConverter().toPosts(jsonDeResposta);

            return postsRecebidos;
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<BlogPost> retorno) {
        MyLog.i("RETORNO OBTIDO!" + retorno);

        if (retorno!=null) {
            this.activity.atualizaListaCom(retorno);
        } else {
            Toast.makeText(this.activity, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();
        }
    }
}

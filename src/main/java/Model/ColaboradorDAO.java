package Model;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
public class ColaboradorDAO {
    private Connection conexao;
    public void cadastrar(Colaborador colaborador) {
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoBD.ConexaoBD();
            String sql = "INSERT INTO TAB_COLABORADOR(CODIGO_COLAB, NOME, EMAIL, SALARIO, DATA_CONTRATACAO) VALUES (DEFAULT, ?, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, colaborador.getNome());
            stmt.setString(2, colaborador.getEmail());
            stmt.setDouble(3, colaborador.getSalario());
            java.sql.Date data = new java.sql.Date(colaborador.getDataContratacao().getTimeInMillis());
            stmt.setDate(4, data);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public List<Colaborador> listar() {
        //Cria uma lista de colaboradores
        List<Colaborador> lista = new ArrayList<Colaborador>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConexaoBD.ConexaoBD();
            stmt = conexao.prepareStatement("SELECT * FROM TAB_COLABORADOR");
            rs = stmt.executeQuery();

            //Percorre todos os registros encontrados
            while (rs.next()) {
                int codigo = rs.getInt("CODIGO_COLAB");
                String nome = rs.getString("NOME");
                String email = rs.getString("EMAIL");
                double salario = rs.getDouble("SALARIO");
                java.sql.Date data = rs.getDate("DATA_CONTRATACAO");
                Calendar dataContratacao = Calendar.getInstance();
                dataContratacao.setTimeInMillis(data.getTime());
                //Cria um objeto Colaborador com as informações encontradas
                Colaborador colaborador = new Colaborador(codigo, nome, email, salario, dataContratacao);
                //Adiciona o colaborador na lista
                lista.add(colaborador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
                rs.close();
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    public void remover(int codigo){
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoBD.ConexaoBD();
            String sql = "DELETE FROM TAB_COLABORADOR WHERE CODIGO_COLAB = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
            System.out.println("Exluido");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Deu ruim");
        } finally {
            try {
                stmt.close();
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Colaborador buscarPorId(int codigoBusca){
        Colaborador colaborador = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConexaoBD.ConexaoBD();
            stmt = conexao.prepareStatement("SELECT * FROM TAB_COLABORADOR WHERE CODIGO_COLAB = ?");
            stmt.setInt(1, codigoBusca);
            rs = stmt.executeQuery();

            if (rs.next()){
                int codigo = rs.getInt("CODIGO_COLAB");
                String nome = rs.getString("NOME");
                String email = rs.getString("EMAIL");
                double salario = rs.getDouble("SALARIO");
                java.sql.Date data = rs.getDate("DATA_CONTRATACAO");
                Calendar dataContratacao = Calendar.getInstance();
                dataContratacao.setTimeInMillis(data.getTime());
                colaborador = new Colaborador(codigo, nome, email, salario, dataContratacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
                rs.close();
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return colaborador;
    }

    public void atualizar(Colaborador colaborador){
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoBD.ConexaoBD();
            String sql = "UPDATE TAB_COLABORADOR SET NOME = ?, EMAIL = ?, SALARIO = ?, DATA_CONTRATACAO = ? WHERE CODIGO_COLAB = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, colaborador.getNome());
            stmt.setString(2, colaborador.getEmail());
            stmt.setDouble(3, colaborador.getSalario());
            java.sql.Date data = new java.sql.Date(colaborador.getDataContratacao().getTimeInMillis());
            stmt.setDate(4, data);
            stmt.setInt(5, colaborador.getCodigo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
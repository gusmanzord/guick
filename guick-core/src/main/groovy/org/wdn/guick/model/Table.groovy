package org.wdn.guick.model

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * Classe de modelo (Metadado do projeto) que representa uma Tabela Da banco de dados
 * existente no Projeto.
 */
class Table {

    String owner;
    String name;
    String comment;
    String sequenceName;
    Table inheritanceTable;

    Long count = 0;

    //Constraint primaryKey
    List<Column> columns = new ArrayList<Column>();
    List<Constraint> constraints = new ArrayList<Constraint>();

    // relacionamento bidirencional
    Project project
    Entity entity

    /**
     * Uma tabela pode ter uma chave simples, ou seja uma coluna, ou seu chave pode ser representada
     * por um conjunto de colunas. Este metodo retorna uma lista com um ou mais valores de colunas que compoem
     * a primary key da tabela
     * @return
     */
    public List<Column> getPk() {
        List<Column> pks = new ArrayList<Column>();
        for (Column column : this.columns) {
            if (column.isKey()) {
                pks.add(column);
            }
        }
        return pks;
    }

    /**
     * Metodo "de negocio" que retorna se a tabela eh  uma tabela de relacionamento N x M
     * @return
     */
    public boolean isNMRelationShip() {
        List<Constraint> fkContraints = constraints.each { t -> t.tipo.equals(ConstraintType.Relationship )}
        if (fkContraints.size() < 2 ) {
            return false
        }
        List<Column> cols = new ArrayList<Column>()
        for (Constraint fk : fkContraints ) {
            cols.addAll(fk.thisSideColumns);
        }
        if (!pk.equals(cols)) {
            return false;
        }
        cols = columns.clone()
        cols.removeAll(pk)
        for (Column col : cols) {
            // TODO Ver padrao de infra para campos default SYSDATE
            if ( !col.name.endsWith("DT_ULT_ALTERACAO") || !col.name.endsWith("DT_INCLUSAO")) {
                return false
            }
        }
        return true;
    }

    /**
     *
     * @param project
     */
    void setProject(Project project) {
        this.project = project
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().
                append(owner).
                append(name).
                toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Table rhs = (Table) obj;
        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(owner, rhs.owner)
                .isEquals();
    }

    @Override
    public String toString(){
        return name
    }
}
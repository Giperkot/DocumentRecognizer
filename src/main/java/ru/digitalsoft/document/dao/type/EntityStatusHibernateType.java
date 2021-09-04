package ru.digitalsoft.document.dao.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;
import ru.digitalsoft.document.enums.EEntityStatus;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class EntityStatusHibernateType  implements DynamicParameterizedType, UserType {

    private Class<? extends Enum> enumClass;

    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        String label = rs.getString(names[0]);
        if (rs.wasNull()) {
            return null;
        }

        return EEntityStatus.getByName(label);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.VARCHAR);
        } else {
            preparedStatement.setString(index, ((EEntityStatus) value).getName());
        }
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }

    @Override
    public Class<? extends Enum> returnedClass() {
        return enumClass;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public void setParameterValues(Properties parameters) {
        DynamicParameterizedType.ParameterType params = (DynamicParameterizedType.ParameterType) parameters.get(PARAMETER_TYPE);
        enumClass = params.getReturnedClass();
    }

}

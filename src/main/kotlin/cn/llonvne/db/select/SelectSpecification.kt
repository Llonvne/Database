package cn.llonvne.db.select

import cn.llonvne.db.table.TableDefinition
import kotlin.reflect.KProperty1

sealed interface SelectSpecification {

    data class From(val tableDefinition: List<TableDefinition<*>>) : SelectSpecification

    data class Fields(val fields: List<KProperty1<*, *>>) : SelectSpecification

    data class Where(val condition: WhereCondition) : SelectSpecification
}




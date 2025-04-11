package cn.llonvne.db.foreignkey

import cn.llonvne.db.table.TableDefinition
import kotlin.reflect.KProperty1

sealed interface ForeignKeySpecification {
    data class ForeignKey<T : Any, F : Any, E>(
        val local: KProperty1<T, E>,
        val foreign: KProperty1<F, E>,
        val foreignTableDefinition: TableDefinition<F>,
        val cascade: CascadeSpecification? = null
    ) : ForeignKeySpecification

    sealed interface CascadeSpecification {

        fun onWhat(): String

        val operation: CascadeOperationSpecification

        data class OnDelete(override val operation: CascadeOperationSpecification) : CascadeSpecification {
            override fun onWhat(): String {
                return "DELETE"
            }
        }

        data class OnUpdate(override val operation: CascadeOperationSpecification) : CascadeSpecification {
            override fun onWhat(): String {
                return "UPDATE"
            }
        }
    }

    sealed interface CascadeOperationSpecification {
        data object Cascade : CascadeOperationSpecification {
            override fun toString(): String {
                return "CASCADE"
            }
        }

        data object SetNull : CascadeOperationSpecification {
            override fun toString(): String {
                return "SET NULL"
            }
        }

        data object Restrict : CascadeOperationSpecification {
            override fun toString(): String {
                return "RESTRICT"
            }
        }

        data object NoAction : CascadeOperationSpecification {
            override fun toString(): String {
                return "NO ACTION"
            }
        }

        data object SetDefault : CascadeOperationSpecification {
            override fun toString(): String {
                return "SET DEFAULT"
            }
        }
    }
}
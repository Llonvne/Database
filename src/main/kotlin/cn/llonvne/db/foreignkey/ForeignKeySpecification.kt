package cn.llonvne.db.foreignkey

import kotlin.reflect.KProperty1

sealed interface ForeignKeySpecification {
    data class ForeignKey<T, F, E>(
        val local: KProperty1<T, E>,
        val foreign: KProperty1<F, E>,
        val cascade: CascadeOperationSpecification? = null
    ) : ForeignKeySpecification

    sealed interface CascadeSpecification {

        val operation: CascadeOperationSpecification

        data class OnDelete(override val operation: CascadeOperationSpecification) : CascadeSpecification

        data class OnUpdate(override val operation: CascadeOperationSpecification) : CascadeSpecification
    }

    sealed interface CascadeOperationSpecification {
        data object Cascade : CascadeOperationSpecification

        data object SetNull : CascadeOperationSpecification

        data object Restrict : CascadeOperationSpecification

        data object NoOperation : CascadeOperationSpecification

        data object SetDefault : CascadeOperationSpecification
    }
}
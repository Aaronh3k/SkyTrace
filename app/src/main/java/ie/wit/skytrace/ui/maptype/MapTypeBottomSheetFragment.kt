package ie.wit.skytrace.ui.maptype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ie.wit.skytrace.databinding.MapTypeBottomSheetBinding

class MapTypeBottomSheetFragment(private val onMapTypeSelected: (Int) -> Unit) : BottomSheetDialogFragment() {

    private var _binding: MapTypeBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapTypeBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.mapTypeNormal.setOnClickListener {
//            onMapTypeSelected(GoogleMap.MAP_TYPE_NORMAL)
//            dismiss()
//        }
        binding.mapTypeSatellite.setOnClickListener {
            onMapTypeSelected(GoogleMap.MAP_TYPE_SATELLITE)
            dismiss()
        }
        binding.mapTypeTerrain.setOnClickListener {
            onMapTypeSelected(GoogleMap.MAP_TYPE_TERRAIN)
            dismiss()
        }
        binding.mapTypeHybrid.setOnClickListener {
            onMapTypeSelected(GoogleMap.MAP_TYPE_HYBRID)
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

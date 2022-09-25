package com.habibi.storyapp.features.story.presentation.maps

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.habibi.core.data.Resource
import com.habibi.core.domain.story.data.StoryItem
import com.habibi.core.utils.getAddressName
import com.habibi.storyapp.R
import com.habibi.storyapp.databinding.FragmentStoryMapsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryMapsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentStoryMapsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StoryMapsViewModel by viewModels()

    private var googleMap: GoogleMap? = null
    private val boundsBuilder = LatLngBounds.Builder()

    private fun setMapStyle() {
        try {
            googleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.maps_style))
        } catch (_: Resources.NotFoundException) { }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initListener()
    }

    private fun setUpMaps() {
        val mapFragment = childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun initListener() {

        binding.btnMapsReload.setOnClickListener {
            viewModel.getListStoryWithLocation()
        }

    }

    private fun initObserver() {
        viewModel.listStoryWithLocation.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    onLoading()
                }
                is Resource.Success -> {
                    onSuccess(it.data)
                }
                is Resource.Failed -> {
                    onFailed(it.message)
                }
                is Resource.Error -> {
                    onError(it.messageResource)
                }
            }
        }
    }

    private fun onLoading() {
        binding.apply {
            groupMapsError.visibility = View.GONE
            map.visibility = View.GONE
            pbMaps.visibility = View.VISIBLE
        }
    }

    private fun onSuccess(listData: List<StoryItem>?) {

        listData?.let {
            if (it.isEmpty()) {
                onEmpty()
            }else {
                binding.apply {
                    groupMapsError.visibility = View.GONE
                    map.visibility = View.VISIBLE
                    pbMaps.visibility = View.GONE
                }
                setUpMaps()
            }
        }
    }

    private fun onFailed(message: String) {
        binding.apply {
            groupMapsError.visibility = View.VISIBLE
            map.visibility = View.GONE
            pbMaps.visibility = View.GONE

            tvMapsMessageError.text = message
            ivMapsImageError.setImageResource(R.drawable.ic_sentiment_very_dissatisfied)
        }
    }

    private fun onEmpty() {
        binding.apply {
            groupMapsError.visibility = View.VISIBLE
            map.visibility = View.GONE
            pbMaps.visibility = View.GONE

            tvMapsMessageError.text = getString(R.string.data_empty)
            ivMapsImageError.setImageResource(R.drawable.ic_sentiment_dissatisfied)
        }
    }

    private fun onError(messageResource: Int) {
        binding.apply {
            groupMapsError.visibility = View.VISIBLE
            map.visibility = View.GONE
            pbMaps.visibility = View.GONE

            tvMapsMessageError.text = getString(messageResource)
            ivMapsImageError.setImageResource(R.drawable.ic_sentiment_dissatisfied)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        setMapStyle()
        this.googleMap?.uiSettings?.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
            isMyLocationButtonEnabled = true
        }
        setUpListMarker()
    }

    private fun setUpListMarker() {
        viewModel.listStoryWithLocation.value?.data?.forEach {
            val latLng = LatLng(it.lat, it.lon)
            val address = requireActivity().getAddressName(it.lat, it.lon)
            googleMap?.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(it.name)
                    .snippet(address)
            )
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}